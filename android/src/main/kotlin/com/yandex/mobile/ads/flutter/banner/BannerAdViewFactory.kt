/*
 * This file is a part of the Yandex Advertising Network
 *
 * Version for Flutter (C) 2022 YANDEX
 *
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at https://legal.yandex.com/partner_ch/
 */

package com.yandex.mobile.ads.flutter.banner

import android.content.Context
import android.view.View
import com.yandex.mobile.ads.banner.AdSize
import com.yandex.mobile.ads.flutter.YandexMobileAdsPlugin
import com.yandex.mobile.ads.flutter.banner.BannerUtil.toDp
import com.yandex.mobile.ads.flutter.banner.command.DestroyBannerCommandHandler
import com.yandex.mobile.ads.flutter.banner.command.LoadBannerCommandHandler
import io.flutter.plugin.common.BinaryMessenger
import io.flutter.plugin.common.EventChannel
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.StandardMessageCodec
import io.flutter.plugin.platform.PlatformView
import io.flutter.plugin.platform.PlatformViewFactory

internal class BannerAdViewFactory(private val messenger: BinaryMessenger) :
    PlatformViewFactory(StandardMessageCodec.INSTANCE) {

    override fun create(context: Context, viewId: Int, args: Any?): PlatformView {
        val params = args as? Map<*, *>
        val adUnitId = params?.get(AD_UNIT_ID) as? String ?: ""
        val adSizeType = params?.get(TYPE) as? String ?: ""
        val id = params?.get(ID) as? Int ?: -1
        val width = params?.get(WIDTH) as? Int ?: 0
        val height = params?.get(HEIGHT) as? Int ?: 0

        val adSize = parseAdSize(context, adSizeType, width, height)

        val bannerView = BannerView(context, adUnitId, adSize)
        val listener = BannerEventListener { getLoadedBannerSize(context, bannerView) }
        bannerView.view.setBannerAdEventListener(listener)

        startFlutterCommunication(id, bannerView, listener)
        return bannerView
    }

    private fun parseAdSize(context: Context, adSizeType: String, width: Int, height: Int): AdSize {
        return when (adSizeType) {
            FLEXIBLE -> AdSize.flexibleSize(width, height)
            STICKY -> AdSize.stickySize(context, width)
            else -> throw IllegalArgumentException("invalid size type")
        }
    }

    private fun getLoadedBannerSize(
        context: Context,
        bannerView: BannerView,
    ): Pair<Int, Int> {
        val bannerAdSize =
            bannerView.view.adSize ?: throw IllegalStateException("adSize does not exists")
        val widthMeasureSpec = getAdSizeMeasureSpec(bannerAdSize.getWidthInPixels(context))
        val heightMeasureSpec = getAdSizeMeasureSpec(bannerAdSize.getHeightInPixels(context))
        bannerView.view.measure(widthMeasureSpec, heightMeasureSpec)
        val density = context.resources.displayMetrics.density
        return bannerView.view.run {
            measuredWidth.toDp(density) to measuredHeight.toDp(density)
        }
    }

    private fun getAdSizeMeasureSpec(sizeInPixels: Int): Int {
        return View.MeasureSpec.makeMeasureSpec(
            sizeInPixels,
            View.MeasureSpec.AT_MOST
        )
    }

    private fun startFlutterCommunication(
        id: Int,
        bannerView: BannerView,
        listener: BannerEventListener
    ) {
        val name = "${YandexMobileAdsPlugin.ROOT}.$BANNER_AD.$id"
        val methodChannel = MethodChannel(messenger, name)
        val eventChannel = EventChannel(messenger, "$name.events")

        val bannerHolder = BannerHolder(bannerView)
        val provider = BannerAdCommandHandlerProvider(
            mapOf(
                LOAD to LoadBannerCommandHandler(bannerHolder),
                DESTROY to DestroyBannerCommandHandler(bannerHolder) {
                    methodChannel.setMethodCallHandler(null)
                    eventChannel.setStreamHandler(null)
                }
            )
        )
        methodChannel.setMethodCallHandler { call, result ->
            provider.commandHandlers[call.method]?.handleCommand(call.method, call.arguments, result)
                ?: result.notImplemented()
        }
        eventChannel.setStreamHandler(listener)
    }

    companion object {

        const val BANNER_AD = "bannerAd"
        const val FLEXIBLE = "flexible"
        const val STICKY = "sticky"

        const val AD_UNIT_ID = "adUnitId"
        const val ID = "id"
        const val WIDTH = "width"
        const val HEIGHT = "height"
        const val TYPE = "type"

        const val LOAD = "load"
        const val DESTROY = "destroy"
    }
}

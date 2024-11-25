/*
 * This file is a part of the Yandex Advertising Network
 *
 * Version for Flutter (C) 2023 YANDEX
 *
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at https://legal.yandex.com/partner_ch/
 */

package com.yandex.mobile.ads.flutter.banner

import android.content.Context
import android.view.View
import com.yandex.mobile.ads.banner.BannerAdSize
import com.yandex.mobile.ads.flutter.YandexMobileAdsPlugin
import com.yandex.mobile.ads.flutter.banner.BannerAdUtil.toDp
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
        val maxHeight = params?.get(HEIGHT) as? Int ?: 0

        val adSize = parseAdSize(context, adSizeType, width, maxHeight)

        val bannerAdView = BannerAdView(context, adUnitId, adSize)
        val listener = BannerAdEventListener { getLoadedBannerSize(context, bannerAdView) }
        bannerAdView.view.setBannerAdEventListener(listener)

        startFlutterCommunication(id, bannerAdView, listener)
        return bannerAdView
    }

    private fun parseAdSize(context: Context, adSizeType: String, width: Int, maxHeight: Int): BannerAdSize {
        return when (adSizeType) {
            INLINE -> BannerAdSize.inlineSize(context, width, maxHeight)
            STICKY -> BannerAdSize.stickySize(context, width)
            else -> BannerAdSize.inlineSize(context, width, maxHeight)
        }
    }

    private fun getLoadedBannerSize(
        context: Context,
        bannerAdView: BannerAdView,
    ): Pair<Int, Int> {
        val bannerAdSize =
            bannerAdView.view.adSize ?: throw IllegalStateException("adSize does not exists")
        val widthMeasureSpec = getAdSizeMeasureSpec(bannerAdSize.getWidthInPixels(context))
        val heightMeasureSpec = getAdSizeMeasureSpec(bannerAdSize.getHeightInPixels(context))
        bannerAdView.view.measure(widthMeasureSpec, heightMeasureSpec)
        val density = context.resources.displayMetrics.density
        return bannerAdView.view.run {
            measuredWidth.toDp(density) to measuredHeight.toDp(density)
        }
    }

    private fun getAdSizeMeasureSpec(sizeInPixels: Int): Int {
        return View.MeasureSpec.makeMeasureSpec(
            sizeInPixels,
            View.MeasureSpec.EXACTLY
        )
    }

    private fun startFlutterCommunication(
        id: Int,
        bannerAdView: BannerAdView,
        listener: BannerAdEventListener
    ) {
        val name = "${YandexMobileAdsPlugin.ROOT}.$BANNER_AD.$id"
        val methodChannel = MethodChannel(messenger, name)
        val eventChannel = EventChannel(messenger, "$name.events")

        val bannerAdHolder = BannerAdHolder(bannerAdView)
        val provider = BannerAdCommandHandlerProvider(
            mapOf(
                LOAD to LoadBannerCommandHandler(bannerAdHolder),
                DESTROY to DestroyBannerCommandHandler(bannerAdHolder) {
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
        const val INLINE = "inline"
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

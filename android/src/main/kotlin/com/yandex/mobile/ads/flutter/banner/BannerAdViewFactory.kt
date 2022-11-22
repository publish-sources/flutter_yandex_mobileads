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
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import com.yandex.mobile.ads.banner.AdSize
import com.yandex.mobile.ads.flutter.YandexMobileAdsPlugin
import com.yandex.mobile.ads.flutter.banner.BannerUtil.toDp
import io.flutter.plugin.common.BinaryMessenger
import io.flutter.plugin.common.EventChannel
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.StandardMessageCodec
import io.flutter.plugin.platform.PlatformView
import io.flutter.plugin.platform.PlatformViewFactory

internal class BannerAdViewFactory(private val messenger: BinaryMessenger)
    : PlatformViewFactory(StandardMessageCodec.INSTANCE) {

    override fun create(context: Context, viewId: Int, args: Any?): PlatformView {
        val params = args as? Map<*, *>
            ?: throw IllegalArgumentException("args is not Map<String, Any?>")
        val adUnitId = params[AD_UNIT_ID] as? String ?: ""
        val id = params[ID] as? Int ?: -1
        val width = params[WIDTH] as? Int ?: 0
        val height = params[HEIGHT] as? Int ?: 0
        val adSize = when (params[TYPE]) {
            FLEXIBLE -> AdSize.flexibleSize(width, height)
            STICKY -> AdSize.stickySize(width)
            else -> throw IllegalArgumentException("invalid size type")
        }

        val bannerView = BannerAdView(context, adUnitId, adSize)
        val listener = BannerAdEventListener { getBannerSize(context, bannerView) }
        bannerView.view.setBannerAdEventListener(listener)
        val name = "${YandexMobileAdsPlugin.ROOT}.$BANNER_AD.$id"
        val methodChannel = MethodChannel(messenger, name)
        val eventChannel = EventChannel(messenger, "$name.events")
        val provider = BannerAdCommandProvider(bannerView) {
            methodChannel.setMethodCallHandler(null)
            eventChannel.setStreamHandler(null)
        }
        methodChannel.setMethodCallHandler { call, result ->
            provider.commands[call.method]?.invoke(call.arguments, result)
                ?: result.notImplemented()
        }
        eventChannel.setStreamHandler(listener)

        return bannerView
    }

    private fun getBannerSize(
        context: Context,
        bannerView: BannerAdView,
    ): Pair<Int, Int> {
        bannerView.view.measure(WRAP_CONTENT, WRAP_CONTENT)
        val density = context.resources.displayMetrics.density
        return bannerView.view.run {
            measuredWidth.toDp(density) to measuredHeight.toDp(density)
        }
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
    }
}

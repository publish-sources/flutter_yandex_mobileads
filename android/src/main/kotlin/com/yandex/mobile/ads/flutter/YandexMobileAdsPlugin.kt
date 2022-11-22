/*
 * This file is a part of the Yandex Advertising Network
 *
 * Version for Flutter (C) 2022 YANDEX
 *
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at https://legal.yandex.com/partner_ch/
 */

package com.yandex.mobile.ads.flutter

import com.yandex.mobile.ads.flutter.banner.BannerAdViewFactory
import com.yandex.mobile.ads.flutter.calls.CreateAdCommandProvider
import com.yandex.mobile.ads.flutter.calls.MobileAdsCommandProvider
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.embedding.engine.plugins.FlutterPlugin.FlutterPluginBinding
import io.flutter.plugin.common.MethodChannel

class YandexMobileAdsPlugin: FlutterPlugin {

    override fun onAttachedToEngine(binding: FlutterPluginBinding) {
        val factory = BannerAdViewFactory(binding.binaryMessenger)
        val context = binding.applicationContext
        binding.platformViewRegistry
            .registerViewFactory(BANNER_VIEW_TYPE, factory)

        val providers = listOf(
            MobileAdsCommandProvider(context),
            CreateAdCommandProvider(context, binding.binaryMessenger),
        )

        providers.forEach { provider ->
            MethodChannel(binding.binaryMessenger, "$ROOT.${provider.name}")
                .setMethodCallHandler { call, result ->
                    provider.commands[call.method]?.invoke(call.arguments, result)
                        ?: result.notImplemented()
                }
        }
    }

    override fun onDetachedFromEngine(binding: FlutterPluginBinding) = Unit

    internal companion object {

        const val ROOT = "yandex_mobileads"
        const val BANNER_VIEW_TYPE = "<banner-ad>"
    }
}

/*
 * This file is a part of the Yandex Advertising Network
 *
 * Version for Flutter (C) 2022 YANDEX
 *
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at https://legal.yandex.com/partner_ch/
 */

package com.yandex.mobile.ads.flutter

import android.content.Context
import com.yandex.mobile.ads.flutter.banner.BannerAdViewFactory
import com.yandex.mobile.ads.flutter.common.AdCreator
import com.yandex.mobile.ads.flutter.common.CreateAdCommandHandlerProvider
import com.yandex.mobile.ads.flutter.common.MobileAdsCommandHandlerProvider
import com.yandex.mobile.ads.flutter.common.command.MobileAdsCommand
import com.yandex.mobile.ads.flutter.common.command.MobileAdsCommandHandler
import com.yandex.mobile.ads.flutter.interstitial.command.CreateInterstitialCommandHandler
import com.yandex.mobile.ads.flutter.rewarded.command.CreateRewardedCommandHandler
import io.flutter.plugin.common.BinaryMessenger
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.embedding.engine.plugins.FlutterPlugin.FlutterPluginBinding
import io.flutter.plugin.common.MethodChannel

class YandexMobileAdsPlugin : FlutterPlugin {

    override fun onAttachedToEngine(binding: FlutterPluginBinding) {
        val factory = BannerAdViewFactory(binding.binaryMessenger)
        val context = binding.applicationContext
        binding.platformViewRegistry
            .registerViewFactory(BANNER_VIEW_TYPE, factory)

        val providers = listOf(
            getMobileAdsCommandHandlerProvider(context),
            getCreateAdHandlerProvider(context, binding.binaryMessenger)
        )

        providers.forEach { provider ->
            MethodChannel(binding.binaryMessenger, "$ROOT.${provider.name}")
                .setMethodCallHandler { call, result ->
                    provider.commandHandlers[call.method]?.handleCommand(
                        call.method,
                        call.arguments,
                        result
                    )
                        ?: result.notImplemented()
                }
        }
    }

    private fun getCreateAdHandlerProvider(
        context: Context,
        messenger: BinaryMessenger,
    ): CreateAdCommandHandlerProvider {
        val adCreator = AdCreator(messenger)
        return CreateAdCommandHandlerProvider(
            mapOf(
                INTERSTITIAL_AD to CreateInterstitialCommandHandler(context, adCreator),
                REWARDED_AD to CreateRewardedCommandHandler(context, adCreator),
            )
        )
    }

    private fun getMobileAdsCommandHandlerProvider(
        context: Context,
    ): MobileAdsCommandHandlerProvider {
        val mobileAdsCommandHandler = MobileAdsCommandHandler(context)
        return MobileAdsCommandHandlerProvider(
            MobileAdsCommand.values().associate { mobileAdsCommand ->
                mobileAdsCommand.command to mobileAdsCommandHandler
            },
        )
    }

    override fun onDetachedFromEngine(binding: FlutterPluginBinding) = Unit

    internal companion object {

        const val ROOT = "yandex_mobileads"
        const val BANNER_VIEW_TYPE = "<banner-ad>"

        const val INTERSTITIAL_AD = "interstitialAd"
        const val REWARDED_AD = "rewardedAd"
    }
}

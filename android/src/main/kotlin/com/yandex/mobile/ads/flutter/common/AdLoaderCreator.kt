/*
 * This file is a part of the Yandex Advertising Network
 *
 * Version for Flutter (C) 2023 YANDEX
 *
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at https://legal.yandex.com/partner_ch/
 */

package com.yandex.mobile.ads.flutter.common

import com.yandex.mobile.ads.flutter.LoadListener
import com.yandex.mobile.ads.flutter.YandexMobileAdsPlugin
import io.flutter.plugin.common.BinaryMessenger
import io.flutter.plugin.common.EventChannel
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.Result

internal class AdLoaderCreator(
    private val messenger: BinaryMessenger,
) {

    fun createAdLoader(
        result: Result,
        channelName: String,
        listener: LoadListener,
        onDestroyHandlerProvider: (onDestroy: () -> Unit) -> CommandHandlerProvider,
    ) {
        val id = idCount++
        val name = "${YandexMobileAdsPlugin.ROOT}.$channelName.$id"
        val methodChannel = MethodChannel(messenger, name)
        val eventChannel = EventChannel(messenger, "$name.events")
        val provider = onDestroyHandlerProvider {
            methodChannel.setMethodCallHandler(null)
            eventChannel.setStreamHandler(null)
        }
        methodChannel.setMethodCallHandler { call, result ->
            provider.commandHandlers[call.method]?.handleCommand(call.method, call.arguments, result)
                ?: result.notImplemented()
        }
        eventChannel.setStreamHandler(listener)
        result.success(id)
    }

    companion object {

        private var idCount = 0
    }
}

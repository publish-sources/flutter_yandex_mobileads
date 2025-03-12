package keys

import com.yandex.plugin_tests_support.ScreenElement

object InterstitialKeys {
    val adUnitId = ScreenElement.WithId("interstitial-ad-unit-id", "текстовое поле Ad Unit ID")
    val log = ScreenElement.WithId("interstitial-log", "кнопку открытия логов")
    val loadAd = ScreenElement.WithId("interstitial-load-ad", "кнопку Load Ad")
    val showAd = ScreenElement.WithId("interstitial-show-ad", "кнопку Show Ad")
    var ad = ScreenElement.WithId(
        iosId = "mac_call_to_action",
        androidIds = listOf("call_to_action", "yma_call_to_action", "mac_call_to_action"),
        name = "рекламу"
    )
    var closeAd = ScreenElement.WithId(
        iosId = "mac_close_button",
        androidIds = listOf("close", "close_view", "close_button", "yma_close_button", "mac_close_button"),
        name = "крестик"
    )
}

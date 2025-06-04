package keys

import com.yandex.plugin_tests_support.ScreenElement

object AppOpenAdKeys {
    val adUnitId = ScreenElement.WithId("banner-ad-unit-id", "текстовое поле Ad Unit ID")
    val log = ScreenElement.WithId("app-open-log", "кнопку открытия логов")
    val loadAd = ScreenElement.WithId("app-open-load-ad", "кнопку Load Ad")
    val showAd = ScreenElement.WithId("app-open-show-ad", "кнопку Show Ad")
    val ad = ScreenElement.WithId(
        iosId = "mac_interstitial",
        androidIds = listOf("yma_root_layout"),
        name = "рекламу"
    )
    val callToAction = ScreenElement.WithId(
        iosId = "mac_call_to_action",
        androidIds = listOf("call_to_action", "yma_call_to_action", "mac_call_to_action"),
        name = "кнопку Call To Action"
    )
    val closeAd = ScreenElement.WithId(
        iosId = "mac_close_button",
        androidIds = listOf("close", "mac_close_button"),
        name = "крестик закрытия рекламы"
    )
}

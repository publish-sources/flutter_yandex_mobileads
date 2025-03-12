package keys

import com.yandex.plugin_tests_support.ScreenElement

object AppOpenAdKeys {
    val log = ScreenElement.WithId("app-open-log", "кнопку открытия логов")
    var loadAd = ScreenElement.WithId("app-open-load-ad", "кнопку Load Ad")
    var showAd = ScreenElement.WithId("app-open-show-ad", "кнопку Show Ad")
    var ad = ScreenElement.WithId(
        iosId = "mac_call_to_action",
        androidIds = listOf("call_to_action", "yma_call_to_action", "mac_call_to_action"),
        name = "рекламу",
        isNative = true
    )
    var closeAd = ScreenElement.WithId(
        iosId = "mac_close_button",
        androidIds = listOf("close", "mac_close_button"),
        name = "крестик закрытия рекламы",
        isNative = true
    )
}

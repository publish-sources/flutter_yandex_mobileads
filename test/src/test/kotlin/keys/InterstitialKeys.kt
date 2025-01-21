package keys

import com.yandex.plugin_tests_support.NativeAccessibilityKey

object InterstitialKeys {
    val adUnitId = "interstitial-ad-unit-id"
    val log = "interstitial-log"
    val loadAd = "interstitial-load-ad"
    val showAd = "interstitial-show-ad"
    var ad = NativeAccessibilityKey(
        iosId = "mac_call_to_action",
        androidIds = listOf("call_to_action", "yma_call_to_action", "mac_call_to_action")
    )
    var closeAd = NativeAccessibilityKey(
        iosId = "mac_close_button",
        androidIds = listOf("close", "close_view", "close_button", "yma_close_button", "mac_close_button")
    )
}

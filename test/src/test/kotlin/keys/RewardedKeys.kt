package keys

import com.yandex.plugin_tests_support.NativeAccessibilityKey

object RewardedKeys {
    val adUnitId = "rewarded-ad-unit-id"
    val log = "rewarded-log"
    val loadAd = "rewarded-load-ad"
    val showAd = "rewarded-show-ad"
    var ad = NativeAccessibilityKey(
        iosId = "mac_call_to_action",
        androidIds = listOf("call_to_action", "yma_call_to_action", "mac_call_to_action")
    )
    var closeAd = NativeAccessibilityKey(
        iosId = "mac_close_button",
        androidIds = listOf("close", "close_view", "close_button", "yma_close_button", "mac_close_button")
    )
}

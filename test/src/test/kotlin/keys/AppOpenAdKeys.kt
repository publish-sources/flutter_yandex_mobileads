package keys

import support.NativeAccessibilityKey

class AppOpenAdKeys {
    companion object {
        val log = "app-open-log"
        var loadAd = "app-open-load-ad"
        var showAd = "app-open-show-ad"
        var ad = NativeAccessibilityKey(
            iosId = "mac_call_to_action",
            androidIds = listOf("call_to_action", "yma_call_to_action", "mac_call_to_action")
        )
        var closeAd = NativeAccessibilityKey(
            iosId = "mac_close_button",
            androidIds = listOf("close", "mac_close_button")
        )
    }
}

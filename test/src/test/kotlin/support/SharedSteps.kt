package support

import com.yandex.plugin_tests_support.BaseTest
import com.yandex.plugin_tests_support.enterText

object ScreenName {
    const val Banner = "banner"
    const val Interstitial = "interstitial"
    const val Rewarded = "rewarded"
}

fun BaseTest.setAdUnitId(screen: String, adUnitId: String) {
    enterText("$screen-ad-unit-id", adUnitId)
}

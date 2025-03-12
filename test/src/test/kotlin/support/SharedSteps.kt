package support

import com.yandex.plugin_tests_support.BaseTest
import com.yandex.plugin_tests_support.ScreenElement
import com.yandex.plugin_tests_support.enterText
import keys.BannerKeys
import keys.InterstitialKeys
import keys.RewardedKeys

enum class ScreenName {
    Banner {
        override fun adUnitIdField() = BannerKeys.adUnitId
    }, Interstitial {
        override fun adUnitIdField() = InterstitialKeys.adUnitId
    }, Rewarded {
        override fun adUnitIdField() = RewardedKeys.adUnitId
    };

    abstract fun adUnitIdField(): ScreenElement
}

fun BaseTest.setAdUnitId(screen: ScreenName, adUnitId: String) {
    enterText(screen.adUnitIdField(), adUnitId)
}

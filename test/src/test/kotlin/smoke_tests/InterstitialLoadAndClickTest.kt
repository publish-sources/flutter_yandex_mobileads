package smoke_tests

import callbacks.InterstitialCallbacks
import io.qameta.allure.Epic
import io.qameta.allure.Story
import keys.HomeKeys
import keys.InterstitialKeys
import org.testng.annotations.Test
import com.yandex.plugin_tests_support.returnToApp
import com.yandex.plugin_tests_support.waitAndClick
import com.yandex.plugin_tests_support.assertBrowserOpened
import com.yandex.plugin_tests_support.testPalm
import support.*

@Epic("E2E тесты")
@Story("Flutter Загрузка, клик и закрытие Interstitial рекламы")
class InterstitialLoadAndClickTest: BaseFlutterTest() {
    @Test
    fun loadInterstitialAdAndClick() {
        testPalm(3845)

        waitAndClick(HomeKeys.interstitialPage)
        waitAndClick(InterstitialKeys.log)
        waitAndClick(InterstitialKeys.loadAd)
        waitLogsCallback(InterstitialCallbacks.loaded)
        waitAndClick(InterstitialKeys.showAd)
//        waitAndClick(InterstitialKeys.ad)
//        assertBrowserOpened()
//        returnToApp()
//        waitAndClick(InterstitialKeys.closeAd)
//        listOf(
//            InterstitialCallbacks.shown,
//            InterstitialCallbacks.clicked,
//            InterstitialCallbacks.impression,
//            InterstitialCallbacks.dismissed
//        ).forEach { callback -> waitLogsCallback(callback) }
    }
}

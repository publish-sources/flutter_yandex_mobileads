package smoke_tests

import callbacks.InterstitialCallbacks
import io.qameta.allure.Epic
import io.qameta.allure.Story
import keys.HomeKeys
import keys.InterstitialKeys
import org.testng.annotations.Test
import com.yandex.plugin_tests_support.returnToApp
import com.yandex.plugin_tests_support.waitAndClick
import com.yandex.plugin_tests_support.backgroundApp
import com.yandex.plugin_tests_support.testName
import com.yandex.plugin_tests_support.testPalm
import com.yandex.plugin_tests_support.waitForElement
import com.yandex.plugin_tests_support.waitForElement
import io.qameta.allure.AllureId
import support.*
import java.time.Duration

@Epic("E2E тесты")
@Story("Flutter Загрузка, клик и закрытие Interstitial рекламы")
class InterstitialLoadAndClickTest: BaseFlutterTest() {

    @Test
    fun loadInterstitialAdAndClick() {
        testPalm(3845)
        testName("Загрузка, клик и закрытие Interstitial рекламы")

        waitAndClick(HomeKeys.interstitialPage)
        waitAndClick(InterstitialKeys.log)
        waitAndClick(InterstitialKeys.loadAd)
        waitLogsCallback(InterstitialCallbacks.loaded)
        waitAndClick(InterstitialKeys.showAd)
        waitForElement(InterstitialKeys.ad)
        waitAndClick(InterstitialKeys.callToAction)
//        assertBrowserOpened()
        returnToApp()
        waitAndClick(InterstitialKeys.skipButton)
        waitAndClick(InterstitialKeys.closeAd)
        listOf(
            InterstitialCallbacks.shown,
            InterstitialCallbacks.clicked,
            InterstitialCallbacks.impression,
            InterstitialCallbacks.dismissed
        ).forEach { callback -> waitLogsCallback(callback) }
    }

    @Test
    fun loadInterstitialAdAndBlock() {
        testName("Interstitial: Сворачивание приложения")

        waitAndClick(HomeKeys.interstitialPage)
        waitAndClick(InterstitialKeys.log)
        waitAndClick(InterstitialKeys.loadAd)
        waitLogsCallback(InterstitialCallbacks.loaded)
        waitAndClick(InterstitialKeys.showAd)
        backgroundApp(Duration.ofSeconds(10), true)
        waitForElement(InterstitialKeys.ad)
    }

    @Test
    fun loadInterstitialInvalidAd() {
        testPalm(4281)
        testName("Interstitial: Загрузка рекламы с некорректным блоком")

        waitAndClick(HomeKeys.interstitialPage)
        waitAndClick(InterstitialKeys.log)
        setAdUnitId(ScreenName.Interstitial, "demo-interstitial-yandex1")
        waitAndClick(InterstitialKeys.loadAd)
        waitLogsCallback(InterstitialCallbacks.notExist)
    }
}

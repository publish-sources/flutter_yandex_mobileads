package smoke_tests

import callbacks.AppOpenAdCallbacks
import com.yandex.plugin_tests_support.Functionalities.AppOpenAd
import com.yandex.plugin_tests_support.allureStep
import com.yandex.plugin_tests_support.assertBrowserOpened
import com.yandex.plugin_tests_support.backgroundApp
import com.yandex.plugin_tests_support.rotateScreen
import com.yandex.plugin_tests_support.returnToApp
import com.yandex.plugin_tests_support.testName
import com.yandex.plugin_tests_support.temporarilyLockScreen
import com.yandex.plugin_tests_support.waitAndClick
import com.yandex.plugin_tests_support.waitForElement
import io.qameta.allure.Epic
import io.qameta.allure.Feature
import io.qameta.allure.Story
import keys.AppOpenAdKeys
import keys.HomeKeys
import org.testng.annotations.DataProvider
import org.testng.annotations.Test
import org.openqa.selenium.ScreenOrientation
import support.ScreenName
import support.setAdUnitId
import support.waitLogsCallback
import java.time.Duration

@Epic("E2E тесты")
@Story("AppOpenAd Загрузка и клик по рекламе")
@Feature(AppOpenAd)
class AppOpenAdLoadAndClickTest: BaseFlutterTest() {

    @DataProvider(name = "demoBlocksProvider")
    fun demoBlocks(): Array<String> {
        return arrayOf(
            "test-appopenad-direct-rmp",
            "demo-appopenad-yandex",
            "demo-appopenad-admob"
        )
    }

    @Test
    fun loadAppOpenAdAndClick() {
        testName("AppOpenAd: Загрузка и клик по рекламе")

        waitAndClick(HomeKeys.appOpenAd)
        waitAndClick(AppOpenAdKeys.log)
        setAdUnitId(ScreenName.AppOpenAd, "demo-appopenad-yandex", true)
        waitAndClick(AppOpenAdKeys.loadAd)
        waitLogsCallback(AppOpenAdCallbacks.loaded)
        waitAndClick(AppOpenAdKeys.showAd)
        waitForElement(AppOpenAdKeys.ad)
        waitAndClick(AppOpenAdKeys.callToAction)
        assertBrowserOpened()
        returnToApp()
        waitAndClick(AppOpenAdKeys.closeAd)
        listOf(
            AppOpenAdCallbacks.shown,
            AppOpenAdCallbacks.clicked,
            AppOpenAdCallbacks.impression,
            AppOpenAdCallbacks.dismissed
        ).forEach { callback -> waitLogsCallback(callback) }
    }

    @Test
    fun loadAppOpenAdAndBlock() {
        testName("Flutter Блокировка приложения")

        waitAndClick(HomeKeys.appOpenAd)
        waitAndClick(AppOpenAdKeys.log)
        setAdUnitId(ScreenName.AppOpenAd, "demo-appopenad-yandex", true)
        waitAndClick(AppOpenAdKeys.loadAd)
        waitLogsCallback(AppOpenAdCallbacks.loaded)
        waitAndClick(AppOpenAdKeys.showAd)
        waitForElement(AppOpenAdKeys.ad)
        temporarilyLockScreen(Duration.ofSeconds(10))
        waitForElement(AppOpenAdKeys.ad)
        waitAndClick(AppOpenAdKeys.closeAd)
        waitLogsCallback(AppOpenAdCallbacks.impression)
    }

    @Test
    fun loadErrorTest() {
        testName("Flutter Ошибка загрузки appopenad в landscape")

        waitAndClick(HomeKeys.appOpenAd)
        waitAndClick(AppOpenAdKeys.log)
        allureStep("Поворачиваем экран в ориентацию Landscape") {
            rotateScreen(ScreenOrientation.LANDSCAPE)
        }
        setAdUnitId(ScreenName.AppOpenAd, "demo-appopenad-yandex", true)
        waitAndClick(AppOpenAdKeys.loadAd)
        waitLogsCallback(AppOpenAdCallbacks.failedToLoad)
    }

    @Test
    fun loadAppOpenAdHideApp() {
        testName("AppOpenAd: Сворачивание приложения")

        waitAndClick(HomeKeys.appOpenAd)
        waitAndClick(AppOpenAdKeys.log)
        setAdUnitId(ScreenName.AppOpenAd, "demo-appopenad-yandex", true)
        waitAndClick(AppOpenAdKeys.loadAd)
        waitLogsCallback(AppOpenAdCallbacks.loaded)
        waitAndClick(AppOpenAdKeys.showAd)
        backgroundApp(Duration.ofSeconds(10), true)
        waitForElement(AppOpenAdKeys.ad)
    }

    @Test
    fun loadAppOpenInvalidAd() {
        testName("AppOpenAd: Загрузка рекламы с некорректным блоком")

        waitAndClick(HomeKeys.appOpenAd)
        waitAndClick(AppOpenAdKeys.log)
        setAdUnitId(ScreenName.AppOpenAd, "demo-appopenad-yandex1")
        waitAndClick(AppOpenAdKeys.loadAd)
        waitLogsCallback(AppOpenAdCallbacks.notExist)
    }
}

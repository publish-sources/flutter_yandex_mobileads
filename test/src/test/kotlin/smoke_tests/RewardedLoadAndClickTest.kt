package smoke_tests

import callbacks.RewardedCallbacks
import io.qameta.allure.Epic
import io.qameta.allure.Story
import keys.HomeKeys
import keys.RewardedKeys
import org.testng.annotations.Test
import com.yandex.plugin_tests_support.waitAndClick
import com.yandex.plugin_tests_support.backgroundApp
import com.yandex.plugin_tests_support.assertBrowserOpened
import com.yandex.plugin_tests_support.testName
import com.yandex.plugin_tests_support.testPalm
import com.yandex.plugin_tests_support.waitForElement
import support.*
import java.time.Duration

@Epic("E2E тесты")
@Story("Flutter Загрузка, клик и закрытие Rewarded рекламы")
class RewardedLoadAndClickTest: BaseFlutterTest() {

    @Test
    fun loadRewardedAdAndClick() {
        testPalm(3853)
        testName("Загрузка, клик и закрытие Rewarded рекламы")

        waitAndClick(HomeKeys.rewardedPage)
        waitAndClick(RewardedKeys.log)
        waitAndClick(RewardedKeys.loadAd)
        waitLogsCallback(RewardedCallbacks.loaded)
        waitAndClick(RewardedKeys.showAd)
//        waitForElement(RewardedKeys.ad, checkForDisplay = false)
//        waitAndClick(RewardedKeys.callToAction)
//        assertBrowserOpened()
//        returnToApp()
//        waitAndClick(RewardedKeys.closeAd)
//        listOf(
//            RewardedCallbacks.shown,
//            RewardedCallbacks.clicked,
//            RewardedCallbacks.impression,
//            RewardedCallbacks.dismissed
//        ).forEach { callback -> waitLogsCallback(callback) }
    }

    @Test
    fun loadRewardedAdAndBlock() {
        testName("Rewarded: Сворачивание приложения")

        waitAndClick(HomeKeys.rewardedPage)
        waitAndClick(RewardedKeys.log)
        waitAndClick(RewardedKeys.loadAd)
        waitLogsCallback(RewardedCallbacks.loaded)
        waitAndClick(RewardedKeys.showAd)
        backgroundApp(Duration.ofSeconds(10), true)
        waitForElement(RewardedKeys.ad)
    }

    @Test
    fun loadAppOpenInvalidAd() {
        testPalm(4281)
        testName("Rewarded: Загрузка рекламы с некорректным блоком")

        waitAndClick(HomeKeys.rewardedPage)
        waitAndClick(RewardedKeys.log)
        setAdUnitId(ScreenName.Rewarded, "demo-rewarded-yandex1")
        waitAndClick(RewardedKeys.loadAd)
        waitLogsCallback(RewardedCallbacks.notExist)
    }
}

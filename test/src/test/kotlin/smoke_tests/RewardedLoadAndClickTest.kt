package smoke_tests

import callbacks.RewardedCallbacks
import io.qameta.allure.Epic
import io.qameta.allure.Story
import keys.HomeKeys
import keys.RewardedKeys
import org.testng.annotations.Test
import com.yandex.plugin_tests_support.returnToApp
import com.yandex.plugin_tests_support.waitAndClick
import com.yandex.plugin_tests_support.assertBrowserOpened
import support.*
import support.annotations.TestPalm

@Epic("E2E тесты")
@Story("Flutter Загрузка, клик и закрытие Rewarded рекламы")
class RewardedLoadAndClickTest: BaseFlutterTest() {
    @Test
    @TestPalm(3853)
    fun loadRewardedAdAndClick() {
        waitAndClick(HomeKeys.rewardedPage)
        waitAndClick(RewardedKeys.log)
        waitAndClick(RewardedKeys.loadAd)
        waitLogsCallback(RewardedCallbacks.loaded)
        waitAndClick(RewardedKeys.showAd)
        waitAndClick(RewardedKeys.ad)
        assertBrowserOpened()
        returnToApp()
        waitAndClick(RewardedKeys.closeAd)
        listOf(
            RewardedCallbacks.shown,
            RewardedCallbacks.clicked,
            RewardedCallbacks.impression,
            RewardedCallbacks.dismissed
        ).forEach { callback -> waitLogsCallback(callback) }
    }
}

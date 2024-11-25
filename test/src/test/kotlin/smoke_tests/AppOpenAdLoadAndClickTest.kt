package smoke_tests

import support.BaseTest
import callbacks.AppOpenAdCallbacks
import io.qameta.allure.Epic
import io.qameta.allure.Story
import keys.AppOpenAdKeys
import keys.HomeKeys
import org.testng.annotations.Test
import support.*

@Epic("E2E тесты")
@Story("Flutter AppOpenAd Загрузка и клик по рекламе")
class AppOpenAdLoadAndClickTest: BaseTest() {
    @Test
    fun loadAppOpenAdAndClick() {
        waitAndClick(HomeKeys.appOpenAd)
        waitAndClick(AppOpenAdKeys.log)
        waitAndClick(AppOpenAdKeys.loadAd)
        waitLogsCallback(AppOpenAdCallbacks.loaded)
        waitAndClick(AppOpenAdKeys.showAd)
        waitAndClick(AppOpenAdKeys.ad)
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
}

package smoke_tests

import callbacks.AppOpenAdCallbacks
import com.yandex.plugin_tests_support.Functionalities.AppOpenAd
import com.yandex.plugin_tests_support.assertBrowserOpened
import com.yandex.plugin_tests_support.returnToApp
import com.yandex.plugin_tests_support.waitAndClick
import io.qameta.allure.Epic
import io.qameta.allure.Feature
import io.qameta.allure.Story
import keys.AppOpenAdKeys
import keys.HomeKeys
import org.testng.annotations.Test
import support.waitLogsCallback

@Epic("E2E тесты")
@Story("AppOpenAd Загрузка и клик по рекламе")
@Feature(AppOpenAd)
class AppOpenAdLoadAndClickTest: BaseFlutterTest() {
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

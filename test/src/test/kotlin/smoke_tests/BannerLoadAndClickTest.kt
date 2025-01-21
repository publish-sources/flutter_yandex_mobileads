package smoke_tests

import callbacks.BannerCallbacks
import com.yandex.plugin_tests_support.BannerSizeType
import com.yandex.plugin_tests_support.assertBrowserOpened
import com.yandex.plugin_tests_support.returnToApp
import com.yandex.plugin_tests_support.setBannerSizeType
import com.yandex.plugin_tests_support.waitAndClick
import io.qameta.allure.Epic
import io.qameta.allure.Story
import keys.BannerKeys
import keys.HomeKeys
import org.testng.annotations.DataProvider
import org.testng.annotations.Test
import support.annotations.TestPalm
import support.waitLogsCallback

@Epic("E2E тесты")
@Story("Flutter: Загрузка и клик по баннеру")
class BannerLoadAndClickTest: BaseFlutterTest() {
    @DataProvider(name = "sizeTypeProvider")
    fun sizeTypes(): Array<BannerSizeType> {
        return arrayOf(
            BannerSizeType.Inline(400, 240),
            BannerSizeType.Sticky(null)
        )
    }

    @Test(dataProvider = "sizeTypeProvider")
    @TestPalm(3825, 3826)
    fun loadBannerAdAndClick(sizeType: BannerSizeType) {
        waitAndClick(HomeKeys.bannerPage)
        waitAndClick(BannerKeys.log)
        setBannerSizeType(sizeType)
        waitAndClick(BannerKeys.loadAd)
        waitLogsCallback(BannerCallbacks.loaded)
        waitAndClick(BannerKeys.banner)
        assertBrowserOpened()
        returnToApp()
        listOf(
            BannerCallbacks.clicked,
            BannerCallbacks.impression,
            BannerCallbacks.leftApp,
//            BannerCallbacks.returnedToApp
        ).forEach { callback -> waitLogsCallback(callback) }
    }
}


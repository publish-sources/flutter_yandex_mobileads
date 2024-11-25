package smoke_tests

import callbacks.AppOpenAdCallbacks
import callbacks.BannerCallbacks
import io.qameta.allure.Epic
import io.qameta.allure.Story
import keys.AppOpenAdKeys
import keys.BannerKeys
import keys.HomeKeys
import org.testng.annotations.DataProvider
import org.testng.annotations.Test
import support.*
import support.banner.BannerSizeType
import support.banner.setBannerSizeType
import java.time.Duration

@Epic("E2E тесты")
@Story("Flutter: Загрузка и клик по баннеру")
class BannerLoadAndClickTest: BaseTest() {
    @DataProvider(name = "sizeTypeProvider")
    fun sizeTypes(): Array<BannerSizeType> {
        return arrayOf(
            BannerSizeType.Inline(400, 240),
            BannerSizeType.Sticky(null)
        )
    }

    @Test(dataProvider = "sizeTypeProvider")
    fun loadBannerAdAndClick(sizeType: BannerSizeType) {
        waitAndClick(HomeKeys.bannerPage)
        waitAndClick(BannerKeys.log)
        setBannerSizeType(sizeType)
        waitAndClick(BannerKeys.loadAd)
        waitLogsCallback(BannerCallbacks.loaded)
        waitAndClick(BannerKeys.ad)
        assertBrowserOpened()
        returnToApp()

        listOf(
            BannerCallbacks.clicked,
            BannerCallbacks.impression,
            BannerCallbacks.leftApp,
            BannerCallbacks.returnedToApp
        ).forEach { callback -> waitLogsCallback(callback) }
    }
}

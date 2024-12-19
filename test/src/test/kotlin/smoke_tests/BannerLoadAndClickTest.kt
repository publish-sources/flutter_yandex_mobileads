package smoke_tests

import assertBrowserOpened
import banner.BannerSizeType
import banner.setBannerSizeType
import callbacks.BannerCallbacks
import io.qameta.allure.Epic
import io.qameta.allure.Story
import keys.BannerKeys
import keys.HomeKeys
import org.testng.annotations.DataProvider
import org.testng.annotations.Test
import returnToApp
import waitAndClick
import waitLogsCallback

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

package smoke_tests

import callbacks.BannerCallbacks
import com.yandex.plugin_tests_support.BannerSizeType
import com.yandex.plugin_tests_support.assertBrowserOpened
import com.yandex.plugin_tests_support.click
import com.yandex.plugin_tests_support.platformDependant
import com.yandex.plugin_tests_support.returnToApp
import com.yandex.plugin_tests_support.setBannerSizeType
import com.yandex.plugin_tests_support.waitAndClick
import support.setAdUnitId
import io.qameta.allure.Epic
import io.qameta.allure.Story
import keys.BannerKeys
import keys.HomeKeys
import org.testng.annotations.DataProvider
import org.testng.annotations.Test
import support.ScreenName
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

    @DataProvider(name = "demoBlocksProvider")
    fun demoBlocks(): Array<String> {
        return arrayOf(
            "demo-banner-admanager",
            "demo-banner-admob",
            "demo-banner-appnext",
            "demo-banner-chartboost",
            "demo-banner-applovin",
            "demo-banner-inmobi",
            "demo-banner-ironsource",
            "demo-banner-mintegral",
            "demo-banner-mytarget",
            "test-banner-startapp",
            "demo-banner-unityads",
            "demo-banner-vungle",
            "demo-banner-applovin-max",
            "demo-banner-sticky-bigoads",
        )
    }

    @Test(dataProvider = "sizeTypeProvider")
    @TestPalm(3825, 3826)
    fun loadStickyBannerAdAndClick(sizeType: BannerSizeType) {
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
            BannerCallbacks.leftApp
        ).forEach { callback -> waitLogsCallback(callback) }
        platformDependant(ios = {},
            android = {
                waitLogsCallback(BannerCallbacks.returnedToApp)
            })
    }

    @Test(dataProvider = "sizeTypeProvider")
    @TestPalm(3825, 3826)
    fun loadInlineBannerAdAndClick(sizeType: BannerSizeType) {
        waitAndClick(HomeKeys.bannerPage)
        waitAndClick(BannerKeys.log)
        setBannerSizeType(sizeType)
        waitAndClick(BannerKeys.loadAd)
        waitLogsCallback(BannerCallbacks.loaded)

        when (sizeType) {
            is BannerSizeType.Inline -> waitAndClick(BannerKeys.banner)
            is BannerSizeType.Sticky -> waitAndClick(BannerKeys.banner)
        }

        assertBrowserOpened()
        returnToApp()

        listOf(
            BannerCallbacks.clicked,
            BannerCallbacks.impression,
            BannerCallbacks.leftApp
        ).forEach { callback -> waitLogsCallback(callback) }
        platformDependant(ios = {},
            android = {
            waitLogsCallback(BannerCallbacks.returnedToApp)
        })
    }

    @Test
    fun loadStickyBannerAdAndClick() {
        waitAndClick(HomeKeys.bannerPage)
        waitAndClick(BannerKeys.log)
        waitAndClick(BannerKeys.stickySwitch)
        waitAndClick(BannerKeys.loadAd)
        waitLogsCallback(BannerCallbacks.loaded)
        waitAndClick(BannerKeys.banner)
        assertBrowserOpened()
        returnToApp()
        listOf(
            BannerCallbacks.loaded,
            BannerCallbacks.impression,
            BannerCallbacks.clicked,
            BannerCallbacks.leftApp
        ).forEach { callback -> waitLogsCallback(callback) }
        platformDependant(ios = {},
            android = {
                waitLogsCallback(BannerCallbacks.returnedToApp)
            })
    }
}


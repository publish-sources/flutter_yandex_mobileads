package smoke_tests

import banner.BannerSizeType
import banner.setBannerSizeType
import callbacks.BannerCallbacks
import com.yandex.plugin_tests_support.assertBrowserOpened
import com.yandex.plugin_tests_support.backgroundApp
import com.yandex.plugin_tests_support.platformDependant
import com.yandex.plugin_tests_support.returnToApp
import com.yandex.plugin_tests_support.testName
import com.yandex.plugin_tests_support.waitAndClick
import com.yandex.plugin_tests_support.testPalm
import com.yandex.plugin_tests_support.waitForElement
import io.qameta.allure.AllureId
import io.qameta.allure.Epic
import io.qameta.allure.Story
import keys.BannerKeys
import keys.HomeKeys
import org.testng.annotations.DataProvider
import org.testng.annotations.Test
import support.ScreenName
import support.setAdUnitId
import support.waitLogsCallback
import java.time.Duration

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
    fun loadBannerAdAndClick(sizeType: BannerSizeType) {
        testPalm(3825, 3826)
        testName("Загрузка и клик по баннеру")

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
        platformDependant(ios = {}, android = {
            waitLogsCallback(BannerCallbacks.returnedToApp)
        })
    }

    @Test(dataProvider = "sizeTypeProvider")
    fun loadInlineBannerAdAndClick(sizeType: BannerSizeType) {
        testPalm(3825, 3826)
        testName("Загрузка и клик по Inline баннеру")

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
        testName("Загрузка и клик по Sticky баннеру")

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

    @Test
    fun loadBannerAdAndBlock() {
        testName("Banner: Сворачивание приложения")

        waitAndClick(HomeKeys.bannerPage)
        waitAndClick(BannerKeys.log)
        waitAndClick(BannerKeys.stickySwitch)
        waitAndClick(BannerKeys.loadAd)
        waitLogsCallback(BannerCallbacks.loaded)
        backgroundApp(Duration.ofSeconds(10), true)
        waitForElement(BannerKeys.banner)
    }

    @Test
    fun loadAppOpenInvalidAd() {
        testPalm(4281)
        testName("Banner: Загрузка рекламы с некорректным блоком")

        waitAndClick(HomeKeys.bannerPage)
        waitAndClick(BannerKeys.log)
        setAdUnitId(ScreenName.Banner, "demo-banner-yandex1")
        waitAndClick(BannerKeys.loadAd)
        waitLogsCallback(BannerCallbacks.notExist)
    }
}


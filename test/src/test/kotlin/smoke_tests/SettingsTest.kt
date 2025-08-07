package smoke_tests

import callbacks.BannerCallbacks
import com.yandex.plugin_tests_support.Functionalities.Settings
import com.yandex.plugin_tests_support.allureStep
import com.yandex.plugin_tests_support.assertScreenshot
import com.yandex.plugin_tests_support.assertSwitch
import com.yandex.plugin_tests_support.executeShellCommand
import com.yandex.plugin_tests_support.goBack
import com.yandex.plugin_tests_support.platformDependant
import com.yandex.plugin_tests_support.rotateScreen
import com.yandex.plugin_tests_support.scrollTo
import com.yandex.plugin_tests_support.testName
import com.yandex.plugin_tests_support.waitAndClick
import com.yandex.plugin_tests_support.waitForElement
import io.qameta.allure.Epic
import io.qameta.allure.Feature
import keys.BannerKeys
import keys.DebugPanelKeys
import keys.HomeKeys
import keys.SettingsKeys
import org.openqa.selenium.By
import org.openqa.selenium.ScreenOrientation
import org.testng.annotations.Test
import support.ScreenName
import support.setAdUnitId
import support.waitLogsCallback

@Epic("E2E тесты")
@Feature(Settings)
class SettingsTest: BaseFlutterTest() {

    @Test
    fun testDebugErrorIndicator() {
        testName("Flutter Debug Error Indicator")

        waitAndClick(HomeKeys.settingsPage)
        waitAndClick(SettingsKeys.debugErrorIndicator)
        goBack()
        waitAndClick(HomeKeys.bannerPage)
        waitAndClick(BannerKeys.log)
        setAdUnitId(ScreenName.Banner, "demo-banner-yandex", true)
        waitAndClick(BannerKeys.loadAd)
        waitLogsCallback(BannerCallbacks.loaded)
        waitForElement(BannerKeys.banner)
    }

    @Test
    fun testDebugPanelSettings() {
        testName("Flutter Настройки в дебаг панели")
        rotateScreen(ScreenOrientation.PORTRAIT)

        waitAndClick(HomeKeys.settingsPage)
        waitAndClick(SettingsKeys.userConsent)
        waitAndClick(SettingsKeys.locationConsent)
        waitAndClick(SettingsKeys.ageRestrictedUser)
        assertSwitch(SettingsKeys.userConsent, true)
        assertSwitch(SettingsKeys.locationConsent, true)
        assertSwitch(SettingsKeys.ageRestrictedUser, true)
        goBack()
        waitAndClick(HomeKeys.debugPanel)
        scrollTo(getDebugPanelSetting(DebugPanelKeys.hasUserConsentKey))
        checkDebugPanelSetting(DebugPanelKeys.ageRestrictedUser, "Yes")
        platformDependant(
            ios = {},
            android = {
                checkDebugPanelSetting(DebugPanelKeys.hasLocationConsent, "Yes")
            }
        )
        checkDebugPanelSetting(DebugPanelKeys.hasUserConsentKey, "Yes")
        goBackFromDebugPanel()
        waitAndClick(HomeKeys.settingsPage)
        waitAndClick(SettingsKeys.userConsent)
        waitAndClick(SettingsKeys.locationConsent)
        waitAndClick(SettingsKeys.ageRestrictedUser)
        assertSwitch(SettingsKeys.userConsent, false)
        assertSwitch(SettingsKeys.locationConsent, false)
        assertSwitch(SettingsKeys.ageRestrictedUser, false)
        goBack()
        waitAndClick(HomeKeys.debugPanel)
        scrollTo(getDebugPanelSetting(DebugPanelKeys.hasUserConsentKey))
        checkDebugPanelSetting(DebugPanelKeys.ageRestrictedUser, "No")
        checkDebugPanelSetting(DebugPanelKeys.hasLocationConsent, "No")
        checkDebugPanelSetting(DebugPanelKeys.hasUserConsentKey, "No")
    }

    @Test
    fun testDebugPanel() {
        testName("Flutter: Проверка DebugPanel")
        rotateScreen(ScreenOrientation.PORTRAIT)

        waitAndClick(HomeKeys.debugPanel)
        platformDependant(ios = {}, android = {
            waitForElement(DebugPanelKeys.root)
            assertScreenshot(DebugPanelKeys.integrationStatus, "integration_status")
        })
        checkDebugPanelSetting("Application ID")
        checkDebugPanelSetting("App Version")
        platformDependant(ios = {
            checkDebugPanelSetting("iOS Version")
        }, android = {
            checkDebugPanelSetting("System")
            checkDebugPanelSetting("API Level")
        })
        checkDebugPanelSetting("SDK Version")
        checkDebugPanelSetting("SDK Integration Status")
        try {
            checkDebugPanelSetting("Completed Integration")
        } catch (_: Exception) {
            checkDebugPanelSetting("Invalid Integration")
        }
        scrollTo(getDebugPanelSetting(DebugPanelKeys.hasUserConsentKey))
        checkDebugPanelSetting(DebugPanelKeys.ageRestrictedUser)
        checkDebugPanelSetting(DebugPanelKeys.hasLocationConsent)
        checkDebugPanelSetting(DebugPanelKeys.hasUserConsentKey)
        checkDebugPanelSetting("TCF Consent")

    }

    private fun goBackFromDebugPanel() {
        allureStep("Вернуться назад") {
            platformDependant(
                ios = {
                    waitAndClick(DebugPanelKeys.backArrow)
                },
                android = {
                    executeShellCommand("input", arrayOf("keyevent", "KEYCODE_BACK"))
                }
            )
        }
    }

    private fun getDebugPanelSetting(setting: String): By {
        return platformDependant(
            ios = {
                return@platformDependant By.ByXPath("//*[@name='${setting}']")
            },
            android = {
                return@platformDependant By.ByXPath("//*[@text='${setting}']")
            }
        )
    }

    private fun checkDebugPanelSetting(setting: String, expectedStatus: String) {
        allureStep("Проверить: в настройке $setting выставлено значение $expectedStatus") {
            val xPath = platformDependant(
                ios = {
                    return@platformDependant By.ByXPath("//*[@name='${setting}']/following-sibling::*[@name='${expectedStatus}']")
                },
                android = {
                    return@platformDependant By.ByXPath("//*[@text='${setting}']/following-sibling::*[@text='${expectedStatus}']")
                }
            )
            waitForElement(xPath)
        }
    }

    private fun checkDebugPanelSetting(setting: String) {
        allureStep("Проверить: в настройке $setting выставлено корректное значение") {
            waitForElement(getDebugPanelSetting(setting))
        }
    }

}

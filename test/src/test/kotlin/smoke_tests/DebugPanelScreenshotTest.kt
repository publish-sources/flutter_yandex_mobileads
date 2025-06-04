package smoke_tests

import com.yandex.plugin_tests_support.assertScreenshot
import io.qameta.allure.Epic
import io.qameta.allure.Story
import keys.DebugPanelKeys
import keys.HomeKeys
import org.testng.annotations.Test
import com.yandex.plugin_tests_support.platformDependant
import com.yandex.plugin_tests_support.testName
import com.yandex.plugin_tests_support.testPalm
import com.yandex.plugin_tests_support.waitAndClick
import com.yandex.plugin_tests_support.waitForElement

@Epic("E2E тесты")
@Story("Flutter: Проверка DebugPanel")
class DebugPanelScreenshotTest: BaseFlutterTest() {
    @Test
    fun debugPanelTest() {
        testName("Flutter: Проверка DebugPanel")
        testPalm(571396)

        waitAndClick(HomeKeys.debugPanel)
        platformDependant(ios = {}, android = {
            waitForElement(DebugPanelKeys.root)
            assertScreenshot(DebugPanelKeys.integrationStatus, "integration_status")
        })
    }
}

package keys

import com.yandex.plugin_tests_support.ScreenArea
import com.yandex.plugin_tests_support.ScreenElement
import org.openqa.selenium.Rectangle

object DebugPanelKeys {
    val root = ScreenElement.WithId(iosId = "null", androidIds = listOf("com.yandex.mobile.ads.flutter.example:id/debug_panel_root"), name = "дебаг-панель", isTag = false)
    val integrationStatus = ScreenArea(Rectangle(15, 555, 55, 175), "integration_status")
}

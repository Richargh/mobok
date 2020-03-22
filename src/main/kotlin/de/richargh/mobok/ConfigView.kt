package de.richargh.mobok

import javafx.geometry.Pos
import javafx.stage.FileChooser
import tornadofx.*

class ConfigView: View() {

    private val configVM: ConfigVM by inject()

    override val root = borderpane {
        left = label("Settings") { alignment = Pos.CENTER }
        center = textfield(configVM.settings) { isEditable = false }
        right = button("Select ") {
            action {
                val dir = chooseFile(
                        "Select Properties",
                        arrayOf(FileChooser.ExtensionFilter("Properties", "*.config")))
                configVM.changeSettingsFile(dir.firstOrNull()?.toPath())

            }
        }
    }
}
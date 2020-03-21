package de.richargh.mobok

import javafx.beans.property.SimpleObjectProperty
import javafx.geometry.Pos
import javafx.stage.FileChooser
import tornadofx.*
import java.nio.file.Path
import java.nio.file.Paths

class ConfigView : View() {
    override var configPath = Paths.get(".", "no.config")

    private val settingsFile = SimpleObjectProperty<Path>().apply {
        onChange(::changeConfigPath)
        set(Paths.get(".", "mobok.config"))
    }

    private fun changeConfigPath(newPath: Path?) {
        println("Changing config from [$configPath] to [$newPath]")
        configPath = newPath
        if(newPath != null)
            initConfig()
    }

    private fun initConfig(){
        config.double("Foo") ?: config.set("Foo" to 2)
        config.jsonArray("People") ?: config.set("People" to listOf("Frodo", "Sam"))
        config.save()
    }

    private val settings = settingsFile.stringBinding { it?.normalize()?.toAbsolutePath()?.toString() ?: "" }

    override val root = borderpane {
        left = label("Settings") { alignment = Pos.CENTER }
        center = textfield(settings) { isEditable = false }
        right = button("Select ") {
            action {
                val dir = chooseFile(
                        "Select Properties",
                        arrayOf(FileChooser.ExtensionFilter("Properties", "*.config")))
                settingsFile.set(dir.firstOrNull()?.toPath())
            }
        }
    }
}
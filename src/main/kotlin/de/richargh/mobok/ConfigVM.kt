package de.richargh.mobok

import javafx.beans.property.SimpleObjectProperty
import tornadofx.ViewModel
import tornadofx.onChange
import tornadofx.stringBinding
import java.nio.file.Path
import java.nio.file.Paths

class ConfigVM: ViewModel() {
    override var configPath = Paths.get("..", "no.config")

    private val events: Events by inject()

    private val settingsFile = SimpleObjectProperty<Path>().apply {
        onChange(::changeConfigPath)
        set(Paths.get("..", "mobok.config"))
    }

    val settings = settingsFile.stringBinding { it?.normalize()?.toAbsolutePath()?.toString() ?: "" }

    private fun changeConfigPath(newPath: Path?) {
        println("Changing config from [$configPath] to [$newPath]")
        configPath = newPath
        if (newPath != null)
            initConfig()
    }

    private fun initConfig(){
        config.double("Foo") ?: config.set("Foo" to 2)
        config.save()

        //        events.configChanged.push()
    }

    fun changeSettingsFile(settingsPath: Path?) {
        settingsFile.set(settingsPath)
    }
}
package de.richargh.mobok

import com.sun.javafx.binding.BidirectionalBinding.bind
import javafx.beans.property.SimpleIntegerProperty
import javafx.geometry.Pos
import javafx.stage.FileChooser
import tornadofx.*

class ConfigView: View() {

    private val configVM: ConfigVM by inject()

    private val count = SimpleIntegerProperty()
    private val name = count.stringBinding { "History ($it)" }

    override val root = borderpane {
        left = vbox(alignment = Pos.TOP_CENTER) { button("Open") }
        center = hbox(alignment = Pos.CENTER) {
            form {
                fieldset {
                    field("Project") { label("Foo") }
                    field("Branch") { label("Bar") }
                    field("Branch") { label("Bar") }
                }
            }
        }
        right = vbox(alignment = Pos.TOP_CENTER) { button(name) { } }
    }

    private fun prefs(){
        var bool: Boolean = false
        var str: String = ""
        preferences("test app") {
            putBoolean("boolean", true)
            bool = getBoolean("boolean key", false)
            str = get("string", "")
        }
    }
}
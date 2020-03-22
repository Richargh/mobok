package de.richargh.mobok

import javafx.beans.property.BooleanProperty
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleStringProperty
import tornadofx.*

class GitProgress: Fragment() {

    private val data = mutableListOf(Step("doFoo")).asObservable()

    override val root = hbox {
        listview(data) {
            cellFormat {
                graphic = cache {
                    hbox(spacing = 5) {
                        checkbox(property = it.doneProperty)
                        label(it.nameProperty) { isEditable = false }
                    }
                }
            }
        }
    }
}


class Step(name: String) {
    val doneProperty: BooleanProperty = SimpleBooleanProperty(false)
    val done by doneProperty

    val nameProperty = SimpleStringProperty(name)
    var name by nameProperty
}
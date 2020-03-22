package de.richargh.mobok

import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleStringProperty
import tornadofx.*

class GitProgress: Fragment() {

    private val data = mutableListOf(Step("doFoo")).asObservable()

    override val root = hbox {
        tableview(data) {
            column("Done", Step::doneProperty)
            column("Name", Step::nameProperty)
        }
    }
}

class Step(name: String) {
    val doneProperty = SimpleBooleanProperty(false)
    var done by doneProperty

    val nameProperty = SimpleStringProperty(name)
    var name by nameProperty
}
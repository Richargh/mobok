package de.richargh.mobok

import javafx.beans.property.SimpleStringProperty

class Member(val name: String) {

    val nameProperty = SimpleStringProperty(name)
}
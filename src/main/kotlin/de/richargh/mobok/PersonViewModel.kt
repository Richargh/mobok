package de.richargh.mobok

import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import tornadofx.getValue
import tornadofx.objectBinding
import tornadofx.setValue
import java.time.LocalDate
import java.time.Period
import java.util.*

class PersonViewModel(firstname: String, lastname: String, birthday: LocalDate, id: UUID = UUID.randomUUID()) {
    val idProperty = SimpleStringProperty(id.toString())
    var id by idProperty

    val firstnameProperty = SimpleStringProperty(firstname)
    var firstname by firstnameProperty

    val lastnameProperty = SimpleStringProperty(lastname)
    var lastname by firstnameProperty

    val birthdayProperty = SimpleObjectProperty(birthday)
    var birthday by birthdayProperty

    // Make age an observable value as well
    val ageProperty = birthdayProperty.objectBinding { Period.between(it, LocalDate.now()).years }
}
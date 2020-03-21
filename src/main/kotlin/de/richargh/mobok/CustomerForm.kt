package de.richargh.mobok

import javafx.scene.control.DatePicker
import javafx.scene.control.TextField
import tornadofx.*
import java.time.LocalDate

class CustomerForm: View("Register Customer") {

    private val events: Events by inject()


    private var firstNameField: TextField by singleAssign()
    private var datepicker: DatePicker by singleAssign()

    override val root = form {
        fieldset("Personal Information") {
            field("Name") {
                firstNameField = textfield("Spencer")
            }

            field("Birthday") {
                datepicker = datepicker { value = LocalDate.now() }
            }
        }

        button("Save") {
            action {
                info("Saving " + firstNameField.text)
            }
        }
    }

}
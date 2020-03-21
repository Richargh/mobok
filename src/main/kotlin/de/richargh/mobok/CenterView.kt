package de.richargh.mobok

import javafx.beans.property.SimpleDoubleProperty
import javafx.scene.control.TextField
import javafx.scene.paint.Color
import kotlinx.coroutines.*
import tornadofx.*
import java.time.LocalDate

class CenterView: CoroutineScope, View() {

    override val coroutineContext = SupervisorJob() +
                                    Dispatchers.Default +
                                    CoroutineName(javaClass.simpleName)

    private val api : Rest by inject()

    private val events: Events by inject()

    var firstNameField: TextField by singleAssign()
    var lastNameField: TextField by singleAssign()
    var ageField: TextField by singleAssign()

    private val personsViewModel = PersonsViewModel()

    init {
        events.personAdded.subscribe { personsViewModel.add(it) }
    }

    override val root = vbox {

        hbox {
            label("First Name")
            firstNameField = textfield()
        }
        hbox {
            label("Last Name")
            lastNameField = textfield()
        }
        hbox {
            label("Age")
            ageField = textfield()
        }
        button("ADD") {
            useMaxWidth = true
            action {
                info("On Add")
                launch {
                    info("Heavy Computation")
                    delay(2000)
                    val person = PersonViewModel(firstNameField.text,
                                                                                      lastNameField.text,
                                                                                      LocalDate.of(2020, 8, 11))
                    withContext(Dispatchers.Main){
                        info("Pushing Event")
                        events.personAdded.push(person)
                    }
                }
            }
        }
        val completion = SimpleDoubleProperty(0.5)
        progressindicator(completion) {}

        tableview(personsViewModel.items) {
            column("First Name", PersonViewModel::firstnameProperty)
            column("Second Name", PersonViewModel::lastnameProperty)
            column("Birthday", PersonViewModel::birthdayProperty)
            column("Age", PersonViewModel::ageProperty).cellFormat {
                text = it.toString()
                style {
                    backgroundColor += if (it != null && it < 18) {
                        c("#8b0000")
                    } else {
                        Color.TRANSPARENT
                    }
                }
            }
            columnResizePolicy = SmartResize.POLICY
        }
    }
}
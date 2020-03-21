package de.richargh.mobok

import javafx.beans.property.SimpleStringProperty
import javafx.geometry.Pos
import javafx.scene.control.TableView
import javafx.scene.control.TextField
import javafx.scene.control.ToggleButton
import javafx.scene.control.ToggleGroup
import javafx.scene.layout.Priority
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.controlsfx.control.SegmentedButton
import tornadofx.*

class CenterView: CoroutineScope, View() {

    override val coroutineContext = SupervisorJob() +
                                    Dispatchers.Default +
                                    CoroutineName(javaClass.simpleName)

    private val events: Events by inject()

    var firstNameField: TextField by singleAssign()
    var lastNameField: TextField by singleAssign()
    var ageField: TextField by singleAssign()

    private val personsViewModel = PersonsViewModel()

    init {
        events.personAdded.subscribe { personsViewModel.add(it) }
    }

    override val root = vbox {
        add(find(MobControlView::class.java))
        add(find(MemberView::class.java))
    }
}
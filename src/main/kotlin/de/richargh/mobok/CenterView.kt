package de.richargh.mobok

import javafx.scene.control.TextField
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import tornadofx.View
import tornadofx.singleAssign
import tornadofx.vbox

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

    }
}

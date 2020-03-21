package de.richargh.mobok

import org.reactfx.EventSource
import tornadofx.Component
import tornadofx.ScopedInstance

class Events: Component(), ScopedInstance {
    val personAdded = EventSource<PersonViewModel>()

    init {
        personAdded.subscribe { info("Notification: Person added") }
    }
}
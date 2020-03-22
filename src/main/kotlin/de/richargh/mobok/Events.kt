package de.richargh.mobok

import javafx.application.Platform
import org.reactfx.EventSource
import tornadofx.Component
import tornadofx.ScopedInstance

class Events: Component(), ScopedInstance {
    val personAdded = EventSource<PersonViewModel>()
    val globalProgress = EventSource<GlobalProgress>()

}

fun <T> EventSource<T>.pushLater(value: T){
    Platform.runLater { push(value) }
}
package de.richargh.mobok

import javafx.animation.KeyFrame
import javafx.animation.Timeline
import javafx.application.Application
import javafx.beans.property.SimpleDoubleProperty

import javafx.event.EventHandler
import javafx.util.Duration
import tornadofx.*

fun main(args: Array<String>) {
    Application.launch(TimerExample::class.java, *args)
}

class TimerExample: App() {
    override val primaryView = TimeView::class
}

class TimeView: View() {
    private var timeline: Timeline? = null
    private val counter = SimpleDoubleProperty(2.0)

    override val root = hbox {
        label("sup")
        label(counter)
        button("Start") {
            action {
                if(timeline != null) {
                    timeline?.stop()
                    timeline = null
                    return@action
                }
                timeline = Timeline().apply {
                    cycleCount = Timeline.INDEFINITE
                    keyFrames.add(
                            KeyFrame(Duration.seconds(1.0), EventHandler {
                                counter.set(counter.get() - 1)
                                if (counter.get() <= 0)
                                    stop()
                            }))
                    playFromStart()
                }
            }
        }
    }
}
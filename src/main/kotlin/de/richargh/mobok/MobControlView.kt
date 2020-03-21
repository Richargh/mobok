package de.richargh.mobok

import javafx.animation.KeyFrame
import javafx.animation.Timeline
import javafx.beans.property.SimpleIntegerProperty
import javafx.event.EventHandler
import javafx.geometry.Pos
import javafx.scene.control.ToggleButton
import javafx.scene.control.ToggleGroup
import javafx.util.Duration
import org.controlsfx.control.SegmentedButton
import tornadofx.*

class MobControlView: View() {

    private var timeline: Timeline? = null

    private val maxSeconds = 15
    private val memberSeconds = SimpleIntegerProperty(0)
    private val memberProgress = memberSeconds.doubleBinding { (it?.toDouble() ?: 0.0) / maxSeconds }

    override val root = vbox {

        val startEndGroup = ToggleGroup()

        val reset = ToggleButton("Reset")

        val start = ToggleButton("Start").apply {
            action {
                if (timeline != null) {
                    timeline?.stop()
                    timeline = null
                    return@action
                }
                timeline = Timeline().apply {
                    cycleCount = Timeline.INDEFINITE
                    keyFrames.add(
                            KeyFrame(Duration.seconds(1.0), EventHandler {
                                memberSeconds.set(memberSeconds.get() + 1)
                                if (memberSeconds.get() >= maxSeconds) {
                                    stop()
                                    memberSeconds.set(0)
                                    println("Member done")
                                }
                            }))
                    playFromStart()
                }
            }
        }
        val next = ToggleButton("Next").apply { tooltip("bar\nlalala") }
        val done = ToggleButton("Done").apply { tooltip("foo") }

        val commit = ToggleButton("Commit")

        startEndGroup.toggles.addAll(reset, start, next, done, commit)

        val mobcontrols = SegmentedButton(start, next, done)

        hbox(spacing = 5, alignment = Pos.CENTER) {
            add(reset)
            add(mobcontrols)
            add(commit)
        }

        progressbar(memberProgress) { useMaxWidth = true }
    }
}
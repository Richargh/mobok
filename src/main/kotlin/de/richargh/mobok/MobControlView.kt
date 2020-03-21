package de.richargh.mobok

import javafx.beans.property.SimpleDoubleProperty
import javafx.geometry.Pos
import javafx.scene.control.ToggleButton
import javafx.scene.control.ToggleGroup
import org.controlsfx.control.SegmentedButton
import tornadofx.*

class MobControlView: View(){

    private val completion = SimpleDoubleProperty(0.5)

    override val root = vbox {

        val startEndGroup = ToggleGroup()

        val reset = ToggleButton("Reset")

        val start = ToggleButton("Start")
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

        progressbar(completion) { useMaxWidth = true }
    }
}
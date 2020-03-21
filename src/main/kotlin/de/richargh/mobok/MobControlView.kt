package de.richargh.mobok

import javafx.geometry.Pos
import javafx.scene.control.ToggleButton
import javafx.scene.control.ToggleGroup
import org.controlsfx.control.SegmentedButton
import tornadofx.View
import tornadofx.hbox
import tornadofx.vbox

class MobControlView: View(){

    override val root = vbox {

        val startEndGroup = ToggleGroup()

        val reset = ToggleButton("Reset")

        val start = ToggleButton("Start")
        val next = ToggleButton("Next")
        val done = ToggleButton("Done")

        val commit = ToggleButton("Commit")

        startEndGroup.toggles.addAll(reset, start, next, done, commit)

        val mobcontrols = SegmentedButton(start, next, done)

        hbox(spacing = 5, alignment = Pos.CENTER) {
            add(reset)
            add(mobcontrols)
            add(commit)
        }
    }
}
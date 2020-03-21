package de.richargh.mobok

import javafx.geometry.Pos
import javafx.scene.control.TableView
import javafx.scene.control.ToggleButton
import javafx.scene.control.ToggleGroup
import javafx.scene.layout.Priority
import org.controlsfx.control.SegmentedButton
import tornadofx.*

class MemberView: View() {

    val members = listOf(
            Member("Robert"))
            .asObservable()

    override val root = vbox {

        val startEndGroup = ToggleGroup()

        val start = ToggleButton("Start")
        val next = ToggleButton("Next")
        val done = ToggleButton("Done")
        val reset = ToggleButton("Reset")

        startEndGroup.toggles.addAll(start, next, done, reset)

        val mobcontrols = SegmentedButton(start, next, done)
        val cancel = SegmentedButton(reset)

        hbox(spacing = 5, alignment = Pos.CENTER) { add(mobcontrols); add(cancel) }

        tableview(members) {
            column("Name", Member::nameProperty)

            columnResizePolicy = TableView.CONSTRAINED_RESIZE_POLICY

            vboxConstraints {
                vGrow = Priority.ALWAYS
            }
        }
    }
}
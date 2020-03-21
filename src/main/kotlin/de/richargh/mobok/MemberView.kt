package de.richargh.mobok

import javafx.scene.control.TableView
import javafx.scene.layout.Priority
import tornadofx.*

class MemberView: View() {

    val members = listOf(
            Member("Robert"))
            .asObservable()

    override val root = vbox {

        tableview(members) {
            column("Name", Member::nameProperty)

            columnResizePolicy = TableView.CONSTRAINED_RESIZE_POLICY

            vboxConstraints {
                vGrow = Priority.ALWAYS
            }
        }
    }
}
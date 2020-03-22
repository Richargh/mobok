package de.richargh.mobok

import javafx.scene.control.TableView
import javafx.scene.layout.Priority
import tornadofx.*

class MemberView: View() {

    private val memberVM: MemberVM by inject()

    override val root = vbox {

        tableview(memberVM.members) {
            column("Name", Member::nameProperty)

            columnResizePolicy = TableView.CONSTRAINED_RESIZE_POLICY

            vboxConstraints {
                vGrow = Priority.ALWAYS
            }
        }

        hbox {
            textfield()
            button("Add Member") {
                action {
                    memberVM.add(Member("Jim"))
                }
            }
        }
    }
}
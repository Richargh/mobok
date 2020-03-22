package de.richargh.mobok

import javafx.collections.FXCollections
import javafx.event.ActionEvent
import javafx.event.Event
import javafx.scene.control.Label
import javafx.scene.layout.BorderPane
import org.controlsfx.control.BreadCrumbBar
import org.controlsfx.control.NotificationPane
import org.controlsfx.control.action.Action
import tornadofx.*
import java.util.*

class GlobalProgressView: View() {

    private val events: Events by inject()

    private val bcb = BreadCrumbBar<String>().apply {
        isAutoNavigationEnabled = false
    }

    private val steps = mutableListOf<ProgressStep>().asObservable().apply {
        onChange { change ->
            val stepNames = change.list.map {
                if(it.status is StepStatus.NOPE)
                    "(X) ${it.name} (${it.status.message})"
                else
                    "${it.name}"
            }
            bcb.selectedCrumb = BreadCrumbBar.buildTreeModel(*stepNames.toTypedArray())
        }
    }

    init {
        events.globalProgress.subscribe {
            steps.setAll(it.steps)
        }
    }

    override val root = vbox {
        val label = Label("Foo")
        val borderPane = BorderPane(label)
        val notificationPane = NotificationPane(borderPane).apply { prefHeight = 40.0 }
        borderPane.heightProperty().onChange { notificationPane.requestLayout() }
        notificationPane.isShowFromTop = false

        button("Change"){
            action {
                val listExampleItems = ArrayList<String>()
                listExampleItems.add("Foo")
                listExampleItems.add("Child")
                val model = BreadCrumbBar.buildTreeModel(*listExampleItems.toTypedArray())
                bcb.selectedCrumb = model


                label.text = "Do you want to save your password?"
                notificationPane.show()

                notificationPane.actions.add(Action("Foo"){ event: ActionEvent ->
                    println("foo clicked")
                    event.consume()
                    notificationPane.content.getChildList()?.clear()
                })
            }
        }
        val listExampleItems = ArrayList<String>()
        listExampleItems.add("Root")
        listExampleItems.add("First Child")
        listExampleItems.add("Second Child")

        val model = BreadCrumbBar.buildTreeModel(*listExampleItems.toTypedArray())
        bcb.selectedCrumb = model

        add(notificationPane)
        add(bcb)
    }
}
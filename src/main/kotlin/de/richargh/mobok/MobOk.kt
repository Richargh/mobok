package de.richargh.mobok

import javafx.application.Application
import javafx.stage.Stage
import kotlinx.coroutines.runBlocking
import tornadofx.App

class MobOk: App(MainView::class, Styles::class) {
    override fun start(stage: Stage) = runBlocking {
        super.start(stage)
    }
}

fun main(args: Array<String>) {
    Application.launch(MobOk::class.java, *args)
}
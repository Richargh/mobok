package de.richargh.mobok

import de.richargh.mobok.git.FileSystemGit
import javafx.animation.KeyFrame
import javafx.animation.Timeline
import javafx.beans.property.SimpleIntegerProperty
import javafx.event.EventHandler
import javafx.util.Duration
import tornadofx.ViewModel
import tornadofx.doubleBinding
import java.io.File

class MobControlVM: ViewModel() {

    private var timeline: Timeline? = null

    private val maxSeconds = 15
    private val memberSeconds = SimpleIntegerProperty(0)
    val memberProgress = memberSeconds.doubleBinding { (it?.toDouble() ?: 0.0) / maxSeconds }

    fun start() {
        val status = FileSystemGit(File(".")).status()
        println(status)

        if (timeline != null) {
            timeline?.stop()
            timeline = null
            return
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
package de.richargh.mobok

import java.awt.Image
import java.awt.Taskbar
import java.io.IOException
import java.io.InputStream
import java.util.*
import java.util.function.Consumer
import javax.imageio.ImageIO

class TaskbarExample {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            if (Taskbar.isTaskbarSupported()) {
                val taskbar = Taskbar.getTaskbar()
                showProvidedFeatures(taskbar)
                performSomeTaskbarChanges(taskbar)
            } else {
                println("Taskbar is not supported on your platform. Unlucky you :(")
            }
        }

        private fun showProvidedFeatures(taskbar: Taskbar) {
            println("Taskbar is supported and provides the following features:")
            val checkFeatureSupport: Consumer<Taskbar.Feature> =
                    Consumer<Taskbar.Feature> { feature: Taskbar.Feature? ->
                        System.out.printf("Feature %s is supported: %s%n",
                                          feature, taskbar.isSupported(feature))
                    }
            Arrays.stream(Taskbar.Feature.values()).forEach(checkFeatureSupport)
        }

        @Throws(IOException::class, InterruptedException::class)
        private fun performSomeTaskbarChanges(taskbar: Taskbar) {
            setImage(taskbar)
            setBadge(taskbar, "1")
            requestUserAttention(taskbar, false) // bounce once
            setBadge(taskbar, "2")
            setBadge(taskbar, "progress")
            requestUserAttention(taskbar, true) // bounce often
            performProgressInteraction(taskbar)
        }

        @Throws(IOException::class, InterruptedException::class)
        private fun setImage(taskbar: Taskbar) {
            if (taskbar.isSupported(Taskbar.Feature.ICON_IMAGE)) {
                val imageInputStream: InputStream = TaskbarExample::class.java.getResourceAsStream("woman-gesturing-OK[iconfinder].png")
                val image: Image = ImageIO.read(imageInputStream)
                taskbar.iconImage = image
                Thread.sleep(2500)
            }
        }

        @Throws(InterruptedException::class)
        private fun setBadge(taskbar: Taskbar, text: String) {
            if (taskbar.isSupported(Taskbar.Feature.ICON_BADGE_TEXT)) {
                println("Setting badge to $text")
                taskbar.setIconBadge(text)
                Thread.sleep(1000)
            }
        }

        @Throws(InterruptedException::class)
        private fun requestUserAttention(taskbar: Taskbar, critical: Boolean) {
            if (taskbar.isSupported(Taskbar.Feature.USER_ATTENTION)) {
                println("Request user attention")
                taskbar.requestUserAttention(true, critical)
                Thread.sleep(2500)
            }
        }

        @Throws(InterruptedException::class)
        private fun performProgressInteraction(taskbar: Taskbar) {
            if (taskbar.isSupported(Taskbar.Feature.PROGRESS_VALUE)) {
                println("perform Progress Interaction")
                for (i in 0..99) {
                    taskbar.setProgressValue(i)
                    Thread.sleep(100)
                }
            }
        }
    }
}
package de.richargh.mobok

import javafx.application.Application
import javafx.application.Platform
import javafx.event.EventHandler
import javafx.stage.Stage
import javafx.stage.StageStyle
import org.reactfx.EventSource
import tornadofx.*
import tornadofx.Component
import java.awt.*
import java.awt.event.ActionEvent
import java.io.IOException
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.*
import javax.imageio.ImageIO
import javax.swing.SwingUtilities

fun main(args: Array<String>) {
    Application.launch(TrayExample::class.java, *args)
}

class TrayExample: App() {

    override val primaryView = Simple::class

    private val events: TrayEvents by inject()

    private lateinit var stage: Stage

    init {
        events.clickedTray.subscribe(::onHide)
    }

    override fun start(stage: Stage) {
        this.stage = stage

        // instructs the javafx system not to exit implicitly when the last application window is shut.
        Platform.setImplicitExit(false)

        SwingUtilities.invokeLater(::addAppToTray)

        stage.initStyle(StageStyle.TRANSPARENT)


        super.start(stage)
    }

    private fun onShow() {
        stage.show()
        stage.toFront()
    }

    private fun onHide(unit: Unit) {
        println("Hide Clicked")
        stage.hide()
    }

    // a timer allowing the tray icon to provide a periodic notification event.
    private val notificationTimer = Timer()

    // format used to display the current time in a tray icon notification.
    private val timeFormat = SimpleDateFormat.getTimeInstance()

    private fun addAppToTray() {
        try {
            // ensure awt toolkit is initialized.
            Toolkit.getDefaultToolkit()

            // app requires system tray support, just exit if there is no support.
            if (!SystemTray.isSupported()) {
                println("No system tray support, application exiting.")
                Platform.exit()
            }

            // set up a system tray icon.
            val tray = SystemTray.getSystemTray()
            val imageInputStream: InputStream =
                    TaskbarExample::class.java.getResourceAsStream("woman-gesturing-OK[iconfinder].png")
            val image: Image = ImageIO.read(imageInputStream)
            val trayIcon = TrayIcon(image)

            // if the user double-clicks on the tray icon, show the main app stage.
            trayIcon.addActionListener { event: ActionEvent? ->
                println("trayIcon was clicked")
                Platform.runLater(this::onShow)
            }

            // if the user selects the default menu item (which includes the app name),
            // show the main app stage.
            val openItem = MenuItem("hello, world")
            openItem.addActionListener { event: ActionEvent? ->
                println("openItem was clicked")
                Platform.runLater(this::onShow)
            }

            // the convention for tray icons seems to be to set the default icon for opening
            // the application stage in a bold font.
            val defaultFont = Font.decode(null)
            val boldFont = defaultFont.deriveFont(Font.BOLD)
            openItem.font = boldFont

            // to really exit the application, the user must go to the system tray icon
            // and select the exit option, this will shutdown JavaFX and remove the
            // tray icon (removing the tray icon will also shut down AWT).
            val exitItem = MenuItem("Exit")
            exitItem.addActionListener { event: ActionEvent? ->
                notificationTimer.cancel()
                tray.remove(trayIcon)
                println("Exit clicked")
                Platform.exit()
            }

            // setup the popup menu for the application.
            val popup = PopupMenu()
            popup.add(openItem)
            popup.addSeparator()
            popup.add(exitItem)
            trayIcon.popupMenu = popup

            // create a timer which periodically displays a notification message.
            notificationTimer.schedule(
                    object: TimerTask() {
                        override fun run() {
                            println("Scheduling timer")
                            SwingUtilities.invokeLater {
                                trayIcon.displayMessage(
                                        "hello",
                                        "The time is now " + timeFormat.format(
                                                Date()),
                                        TrayIcon.MessageType.INFO
                                                       )
                            }
                        }
                    },
                    5000,
                    60000
                                      )

            // add the application tray icon to the system tray.
            tray.add(trayIcon)
        } catch (e: AWTException) {
            println("Unable to init system tray")
            e.printStackTrace()
        } catch (e: IOException) {
            println("Unable to init system tray")
            e.printStackTrace()
        }
    }
}

class TrayEvents: Component(), ScopedInstance {
    val clickedTray = EventSource<Unit>()
}

class Simple: View() {
    private val events: TrayEvents by inject()

    override val root = vbox {
        vbox {
            label("Hi there")
            label("Hi there")
            //                style = "-fx-background-color: rgba(255, 255, 255, 0.5);"
            setPrefSize(300.0, 200.0)
        }

        onMouseClicked = EventHandler {
            events.clickedTray.push(Unit)
        }

        //            onMouseClicked = EventHandler { event: MouseEvent? -> stage.hide() }
    }
}

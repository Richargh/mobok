package de.richargh.mobok

import tornadofx.Stylesheet

import javafx.geometry.Pos
import tornadofx.*

class Styles : Stylesheet() {
    companion object {
        val strikethrough by cssclass()
        val itemRoot by cssclass()
        val closeIcon by cssclass()
        val contentLabel by cssid()
        val title by cssid()
        val addItemRoot by cssclass()
        val mainCheckBox by cssclass()
        val header by cssclass()
        val footer by cssclass()
    }

    init {
        strikethrough {
            text {
                strikethrough = true
            }
        }

        closeIcon {
            fill = c("#cc9a9a")

            and(hover) {
                fill = c("#af5b5e")
            }
        }

        itemRoot {
            padding = box(8.px)
            button {
                backgroundColor += c("transparent")
                padding = box(-2.px)
            }
            alignment = Pos.CENTER_LEFT
        }

        contentLabel {
            fontSize = 1.2.em
        }

        title {
            fontSize = 12.em
            textFill = c(175, 47, 47, 0.5)
        }

        addItemRoot {
            padding = box(1.em)
            textField {
                prefWidth = 200.px
            }
        }

        mainCheckBox {
            padding = box(0.1.em, 1.em, 0.1.em, 0.1.em)
        }

        header {
            alignment = Pos.CENTER
            star {
                alignment = Pos.CENTER_LEFT
            }
        }

        footer {
            padding = box(10.px)
            alignment = Pos.CENTER
            spacing = 20.px
            star {
                spacing = 10.px
            }
        }
    }
}
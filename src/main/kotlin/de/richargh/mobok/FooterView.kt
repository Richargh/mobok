package de.richargh.mobok

import tornadofx.*

class FooterView: View() {

    override val root = vbox {
        add(find(GlobalProgressView::class))
    }
}
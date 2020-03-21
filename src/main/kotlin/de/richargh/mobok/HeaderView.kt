package de.richargh.mobok

import tornadofx.View
import tornadofx.vbox

class HeaderView : View() {

    override val root = vbox {
        add(find<ConfigView>())
    }
}
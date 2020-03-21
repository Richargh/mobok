package de.richargh.mobok

import tornadofx.View
import tornadofx.borderpane

class MainView: View() {

    override val root = borderpane {
        top<HeaderView>()
        center<CenterView>()
        bottom<FooterView>()
    }
}
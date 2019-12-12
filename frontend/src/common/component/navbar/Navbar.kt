package common.component.navbar

import kotlinx.html.js.onClickFunction
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.a
import react.dom.div
import react.dom.img
import react.dom.nav

interface NavbarProps : RProps {
    var goToDefault: () -> Unit
    var goToRegistration: () -> Unit
}

class Navbar : RComponent<NavbarProps, RState>() {
    override fun RBuilder.render() {
        nav(classes = "mb-1 navbar navbar-expand navbar-dark bg-dark") {
            div("container-fluid") {
                a(classes = "navbar-brand", href = "#") {
                    attrs.onClickFunction = {
                        props.goToDefault()
                    }
                    img(src = "/img/rising.svg") {
                        attrs["height"] = "40px"
                    }
                    +" Rise"
                }
                a(classes = "btn", href = "#") {
                    attrs.onClickFunction = {
                        props.goToRegistration()
                    }
                    +"Registration"
                }
            }
        }
    }
}

fun RBuilder.navbar(goToDefault: () -> Unit, goToRegistration: () -> Unit) = child(Navbar::class) {
    attrs.goToDefault = goToDefault
    attrs.goToRegistration = goToRegistration
}
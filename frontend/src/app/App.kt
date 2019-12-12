package app

import common.component.matchsearch.matchSearch
import common.component.navbar.navbar
import notification.component.notificationComponent
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.div
import react.setState
import user.component.registration

interface AppState : RState {
    var currentForm: String
}

class App : RComponent<RProps, AppState>() {
    override fun AppState.init() {
        currentForm = "matchSearch"
    }

    override fun RBuilder.render() {
        div {
            navbar(::goToDefault, ::goToRegistration)
            notificationComponent()
            div("container-fluid") {
                when (state.currentForm) {
                    "matchSearch" -> matchSearch()
                    "registration" -> registration()
                }
            }
        }
    }

    private fun goToDefault() {
        setState {
            currentForm = "matchSearch"
        }
    }

    private fun goToRegistration() {
        setState {
            currentForm = "registration"
        }
    }
}

fun RBuilder.app() = child(App::class) {}

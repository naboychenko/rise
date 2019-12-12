package user.component

import common.status.InputValidationStatus
import flux.Action
import flux.Dispatcher
import kotlinx.html.js.onBlurFunction
import kotlinx.html.js.onChangeFunction
import kotlinx.html.js.onClickFunction
import notification.domain.Notification
import notification.domain.NotificationType
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.events.Event
import user.domain.User
import user.service.UserService
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.a
import react.dom.article
import react.dom.button
import react.dom.div
import react.dom.form
import react.dom.h4
import react.dom.i
import react.dom.input
import react.dom.jsStyle
import react.dom.p
import react.dom.span
import react.setState


@JsModule("jquery")
external fun jQuery(element: String)

interface RegistrationState : RState {
    var user: User
    var confirmedPassword: String
    var passwordIsValid: Boolean
    var passwordMatch: Boolean
    var firstNameStatus: InputValidationStatus
    var lastNameStatus: InputValidationStatus
    var nickNameStatus: InputValidationStatus
}

class Registration : RComponent<RProps, RegistrationState>() {

    private val PASSWORD_MATCH_TOOLTIP_ID: String = "passwordMatchTooltip"
    private val PASSWORD_VALIDATION_TOOLTIP_ID: String = "passwordValidationTooltip"
    private val ERROR_TOOLTIP_TEMPLATE = """<div class="tooltip" role="tooltip"><div class="arrow" ></div><div class="tooltip-inner" style="background-color:#f00"></div></div>"""


    override fun RegistrationState.init() {
        user = User()
        confirmedPassword = ""
        passwordIsValid = true
        passwordMatch = true
        firstNameStatus = InputValidationStatus.DEFAULT
        lastNameStatus = InputValidationStatus.DEFAULT
        nickNameStatus = InputValidationStatus.DEFAULT
    }

    override fun RBuilder.render() {
        div(classes = "row justify-content-center aliment-vertical-center") {
            div(classes = "card bg-light") {
                attrs.jsStyle {
                    width = "32%"
                    maxWidth = 700
                    minWidth = 300
                    marginTop = "5%"

                }
                article(classes = "card-body mx-auto align-items-center") {
                    attrs.jsStyle {
                        width = "60%"
                    }
                    h4(classes = "card-title mt-3 text-center") {
                        +"Create Account"
                    }
                    p(classes = "text-center") {
                        +"Get started with your free account"
                    }
                    form {
                        div(classes = "form-group input-group") {
                            div(classes = "input-group-prepend") {
                                span(classes = "input-group-text " +
                                        if (state.nickNameStatus == InputValidationStatus.VALID)
                                            "text-success"
                                        else {
                                            if (state.nickNameStatus == InputValidationStatus.INVALID)
                                                "text-danger"
                                            else ""
                                        }) { i(classes = "fa fa-hashtag") {} }
                            }
                            input(classes = "form-control " +
                                    if (state.nickNameStatus == InputValidationStatus.VALID)
                                        "is-valid"
                                    else {
                                        if (state.nickNameStatus == InputValidationStatus.INVALID)
                                            "is-invalid"
                                        else ""
                                    }) {
                                attrs["name"] = "nickname"
                                attrs["placeholder"] = "nickname"
                                attrs["type"] = "text"

                                attrs.onChangeFunction = {
                                    val value = getValue(it)
                                    setState {
                                        nickNameStatus = InputValidationStatus.DEFAULT
                                        user.nickName = value
                                    }
                                }
                            }
                        }
                        div(classes = "form-group input-group") {
                            div(classes = "input-group-prepend") {
                                span(classes = "input-group-text " +
                                        if (state.firstNameStatus == InputValidationStatus.VALID)
                                            "text-success"
                                        else {
                                            if (state.firstNameStatus == InputValidationStatus.INVALID)
                                                "text-danger"
                                            else ""
                                        }) { i(classes = "fa fa-user") {} }
                            }
                            input(classes = "form-control " +
                                    if (state.firstNameStatus == InputValidationStatus.VALID)
                                        "is-valid"
                                    else {
                                        if (state.firstNameStatus == InputValidationStatus.INVALID)
                                            "is-invalid"
                                        else ""
                                    }) {
                                attrs["name"] = "firstName"
                                attrs["placeholder"] = "First name"
                                attrs["type"] = "text"
                                attrs.onChangeFunction = {
                                    val value = getValue(it)
                                    setState {
                                        firstNameStatus = InputValidationStatus.DEFAULT
                                        user.firstName = value
                                    }
                                }
                            }
                        }
                    }
                    div(classes = "form-group input-group") {
                        div(classes = "input-group-prepend") {
                            span(classes = "input-group-text " +
                                    if (state.lastNameStatus == InputValidationStatus.VALID)
                                        "text-success"
                                    else {
                                        if (state.lastNameStatus == InputValidationStatus.INVALID)
                                            "text-danger"
                                        else ""
                                    }) { i(classes = "fa fa-user") {} }
                        }
                        input(classes = "form-control " +
                                if (state.lastNameStatus == InputValidationStatus.VALID)
                                    "is-valid"
                                else {
                                    if (state.lastNameStatus == InputValidationStatus.INVALID)
                                        "is-invalid"
                                    else ""
                                }) {
                            attrs["name"] = "lastName"
                            attrs["placeholder"] = "Last name"
                            attrs["type"] = "text"
                            attrs.onChangeFunction = {
                                val value = getValue(it)
                                setState {
                                    lastNameStatus = InputValidationStatus.DEFAULT
                                    user.lastName = value
                                }
                            }
                        }
                    }
                    div(classes = "form-group input-group") {
                        div(classes = "input-group-prepend") {
                            span(classes = "input-group-text") { i(classes = "fa fa-calendar") {} }
                        }
                        input(classes = "form-control") {
                            attrs["name"] = "birthDate"
                            attrs["placeholder"] = "birthday"
                            attrs["type"] = "date"
                            attrs.onChangeFunction = {
                                val value = getValue(it)
                                setState {
                                    user.birthDate = value
                                }
                            }
                        }
                    }
                    div(classes = "form-group input-group") {
                        div(classes = "input-group-prepend") {
                            span(classes = "input-group-text") { i(classes = "fa fa-envelope") {} }
                        }
                        input(classes = "form-control") {
                            attrs["name"] = "email"
                            attrs["placeholder"] = "Email address"
                            attrs["type"] = "email"
                            attrs.onChangeFunction = {
                                val value = getValue(it)
                                setState {
                                    user.email = value
                                }
                            }
                        }
                    }
                    div(classes = "form-group input-group") {
                        div(classes = "input-group-prepend") {
                            span(classes = "input-group-text ${if (state.passwordIsValid) "" else "text-danger"}") {
                                i(classes = "fa fa-lock") {}
                            }
                        }
                        input(classes = "form-control ${if (state.passwordIsValid) "" else "is-invalid"}") {
                            attrs["name"] = "password"
                            attrs["placeholder"] = "Create password"
                            attrs["type"] = "password"
                            attrs.onChangeFunction = {
                                hideValidationTooltip(PASSWORD_VALIDATION_TOOLTIP_ID)
                                val value = getValue(it)
                                setState {
                                    passwordIsValid = true
                                    user.password = value
                                }
                            }
                            attrs.onBlurFunction = {
                                if (validPasswordCheck()) {
                                    hideValidationTooltip(PASSWORD_VALIDATION_TOOLTIP_ID)
                                } else {
                                    showValidationTooltip(PASSWORD_VALIDATION_TOOLTIP_ID)
                                }
                            }
                        }
                        div(classes = "input-group-append") {
                            attrs["id"] = PASSWORD_VALIDATION_TOOLTIP_ID
                            attrs["data-template"] = ERROR_TOOLTIP_TEMPLATE
                            attrs["data-placement"] = "right"
                            attrs["title"] = "Password have to be 8+ characters!"
                            attrs["trigger"] = "manual"
                        }
                    }
                    div(classes = "form-group input-group") {
                        div(classes = "input-group-prepend") {
                            span(classes = "input-group-text ${if (state.passwordMatch) "" else "text-danger"}") {
                                i(classes = "fa fa-lock") {}
                            }
                        }
                        input(classes = "form-control ${if (state.passwordMatch) "" else "is-invalid"}") {
                            attrs["name"] = "repeat"
                            attrs["placeholder"] = "Repeat password"
                            attrs["type"] = "password"

                            attrs.onChangeFunction = {
                                hideValidationTooltip(PASSWORD_MATCH_TOOLTIP_ID)
                                val value = getValue(it)
                                setState {
                                    passwordMatch = true
                                    confirmedPassword = value
                                }
                            }
                        }

                        div(classes = "input-group-append") {
                            attrs["id"] = PASSWORD_MATCH_TOOLTIP_ID
                            attrs["data-placement"] = "right"
                            attrs["data-template"] = ERROR_TOOLTIP_TEMPLATE
                            attrs["title"] = "Password does not match!"
                            attrs["trigger"] = "manual"
                        }

                    }
                    div(classes = "form-group") {
                        button(classes = "btn btn-primary btn-block") {
                            attrs["type"] = "submit"
                            +"Create Account"
                            attrs.onClickFunction = {
                                //TODO delete
                                console.log(JSON.stringify(state.user))
                                validateUser()
                                if (matchPasswordCheck()) {
                                    if (validPasswordCheck()) {
                                        UserService.registerUser(
                                                state.user,
                                                ::successRegistration,
                                                ::failedRegistration)
                                    }
                                } else {

                                    showValidationTooltip(PASSWORD_MATCH_TOOLTIP_ID)

                                }
                            }
                        }
                    }
                    p(classes = "text-center") {
                        +"Have an account?"
                        a(href = "") { +" Log In" }

                    }
                }
            }
        }
    }

    private fun validateUser() {
        var formData = state.user;
        setState {

            nickNameStatus = if (formData.nickName != null && formData.nickName.length > 2) {
                InputValidationStatus.VALID
            } else {
                InputValidationStatus.INVALID
            }
            firstNameStatus = if (formData.firstName != null && formData.firstName.isNotEmpty()) {
                InputValidationStatus.VALID
            } else {
                InputValidationStatus.INVALID
            }
            lastNameStatus = if (formData.lastName != null && formData.lastName.isNotEmpty()) {
                InputValidationStatus.VALID
            } else {
                InputValidationStatus.INVALID
            }
            if (formData.birthDate != null) {

            } else {

            }
            if (formData.email != null && formData.email.isNotEmpty()) {

            } else {

            }

        }
    }

    private fun getValue(event: Event): String {
        return (event.target as HTMLInputElement).value
    }

    private fun successRegistration() {
        Dispatcher.dispatch(
                Action.NOTIFICATION_ADD,
                Notification(NotificationType.SUCCESS,
                        "Registration complete!"))
    }

    private fun failedRegistration() {
        Dispatcher.dispatch(
                Action.NOTIFICATION_ADD,
                Notification(NotificationType.ERROR,
                        "ERROR!"))
    }

    private fun matchPasswordCheck(): Boolean {
        val passwordMatchCheck = state.user.password == state.confirmedPassword
        setState {
            passwordMatch = passwordMatchCheck
        }
        return passwordMatchCheck
    }

    private fun validPasswordCheck(): Boolean {
        val isValid = state.user.password.length > 7
        setState {
            passwordIsValid = isValid
        }
        return isValid
    }

    private fun showValidationTooltip(elementId: String) {
        jQuery("#${elementId}").asDynamic().tooltip("show")
    }

    private fun hideValidationTooltip(elementId: String) {
        jQuery("#${elementId}").asDynamic().tooltip("hide")
    }

    override fun componentWillUnmount() {
        hideValidationTooltip(PASSWORD_MATCH_TOOLTIP_ID)
        hideValidationTooltip(PASSWORD_VALIDATION_TOOLTIP_ID)
    }
}


fun RBuilder.registration() = child(Registration::class) {

}
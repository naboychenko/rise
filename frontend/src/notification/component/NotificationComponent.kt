package notification.component

import kotlinx.html.ButtonType
import notification.domain.Notification
import notification.domain.NotificationType
import notification.store.NotificationStore
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.button
import react.dom.div
import react.dom.span
import react.setState
import kotlin.text.Typography.times

val DANGER_CLASS = " alert-danger"
val SUCCESS_CLASS = " alert-success"
val BASE_NOTIFICATION_CLASS_SET = "alert alert-dismissible fade show"

interface NotificationState : RState {
    var notification: Notification?
}

class NotificationComponent : RComponent<RProps, NotificationState>() {

    var notificationSubscribtionId: Long? = null

    override fun RBuilder.render() {
        val notification = state.notification

        if (notification != null) {

            val contextualClass = when (notification.type) {
                NotificationType.SUCCESS -> SUCCESS_CLASS
                NotificationType.ERROR -> DANGER_CLASS
            }

            div(classes = BASE_NOTIFICATION_CLASS_SET + contextualClass) {

                attrs["role"] = "alert"

                +notification.body

                button(type = ButtonType.button, classes = "close") {
                    attrs["data-dismiss"] = "alert"
                    attrs["aria-label"] = "Close"
                    span {
                        attrs["aria-hidden"] = true
                        +"$times"
                    }
                }
            }
        }
    }

    override fun componentDidMount() {
        notificationSubscribtionId = NotificationStore.subscribe {
            setState {
                notification = NotificationStore.notification
            }
        }
    }

    override fun componentWillUnmount() {
        NotificationStore.unsubscribe(notificationSubscribtionId!!)
    }
}

fun RBuilder.notificationComponent() = child(NotificationComponent::class) {

}
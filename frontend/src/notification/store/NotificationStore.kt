package notification.store

import flux.Action
import flux.Dispatcher
import common.flux.Store
import notification.domain.Notification

object NotificationStore : Store() {

    var notification: Notification? = null
        private set

    init {
        Dispatcher.subscribe(this)
    }

    override fun dispatch(action: Action, payload: Any) {
        when (action) {
            Action.NOTIFICATION_ADD -> {
                notification = payload as Notification
                subscribers.forEach {
                    it.value()
                }
            }
        }

    }
}
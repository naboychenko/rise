package notification.domain

data class Notification(
        var type: NotificationType,
        var body: String
)
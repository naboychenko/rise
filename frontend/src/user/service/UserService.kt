package user.service

import org.w3c.xhr.XMLHttpRequest
import user.domain.User

object UserService {
    fun registerUser(user: User, success: () -> Unit, failure: () -> Unit) {

        var request = XMLHttpRequest()
        var url = "/users"
        request.onreadystatechange = {
            if (request.readyState == XMLHttpRequest.DONE) {
                if (200.rangeTo(299).contains(request.status)) {
                    success()
                } else {
                    failure()
                }
            }
        }
        request.open("POST", url)
        request.setRequestHeader("content-type", "application/json")
        request.send(JSON.stringify(user))
    }
}
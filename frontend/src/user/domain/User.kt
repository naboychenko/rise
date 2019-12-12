package user.domain

data class User(
        var nickName: String = "",
        var firstName: String = "",
        var lastName: String = "",
        var birthDate: String = "",
        var email: String = "",
        var password: String = ""
)
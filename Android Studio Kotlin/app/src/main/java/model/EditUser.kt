package model

data class EditUser(
    var id:Long,
    var password: String,
    var name: String,
    var surname: String,
    var email: String,
    var description: String,
    var studies: String,
    var direction: String,
    var image: ByteArray?
)

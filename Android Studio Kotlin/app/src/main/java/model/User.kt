package model

data class User(
    var id:Long,
    var password: String,
    var name: String,
    var surname: String,
    var email: String,
    var description: String,
    var studies: String,
    var direction: String,
    var image: String
    ) {
    override fun toString(): String {
        return "User(id=$id, password='$password', name='$name', surname='$surname', email='$email', description='$description', studies='$studies', direction='$direction', image_path='$image')"
    }
}


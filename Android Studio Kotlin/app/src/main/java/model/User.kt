package model

import android.text.Editable

data class User(
    var id:Long,
    var password: String,
    var name: String,
    var apellido: String,
    var email: String,
    var description: String,
    var studies: String,
    var direccion: String
    ) {
    override fun toString(): String {
        return "User(id=$id, password='$password', name='$name', apellido='$apellido', email='$email', description='$description', studies='$studies', direccion='$direccion')"
    }
}


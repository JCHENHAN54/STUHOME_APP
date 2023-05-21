package model

data class Booking(
    var id:Long,
    var User:User,
    var userEmail:String,
    var property_booking_name: String,
    var property_booking_description: String,
    var property__price: Int
) {
    override fun toString(): String {
        return "Booking(id=$id, User=$User, userEmail='$userEmail', property_booking_name='$property_booking_name', property_booking_description='$property_booking_description', property__price=$property__price)"
    }
}

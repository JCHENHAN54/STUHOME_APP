package model

import java.io.Serializable

data class Property(
    var id:Long,
    var User:User,
    var userEmail:String,
    var property_name:String,
    var property_address:String,
    var property_city:String,
    var property_description:String,
    var image:ByteArray?,
    var airConditioning:Boolean,
    var petfriendly:Boolean,
    var parking:Boolean,
    var wifi:Boolean,
    var washer:Boolean,
    var smoking:Boolean,
    var property_price:Int,
    var additional_notes:String

) : Serializable {
    override fun toString(): String {
        return "Property(id=$id, User=$User, userEmail='$userEmail', property_name='$property_name', property_address='$property_address', property_city='$property_city', property_description='$property_description', image='$image', airConditioning=$airConditioning, petfriendly=$petfriendly, parking=$parking, wifi=$wifi, washer=$washer, smoking=$smoking, property_price=$property_price, additional_notes='$additional_notes')"
    }
}




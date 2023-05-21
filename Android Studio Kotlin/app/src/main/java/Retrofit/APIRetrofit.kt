package Retrofit

import model.*
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Url

interface APIRetrofit {

    //Api Register.
    @POST
    suspend fun ApiRegister(@Url url: String, @Body user: User): Response<FeedBack>

    //Api Login.
    @POST
    suspend fun ApiLogin(@Url url: String, @Body user: User): Response<FeedBack>

    //Api Profile Login.
    @POST
    suspend fun ApiProfileLogin(@Url url: String, @Body user: User): Response<User>

    //Api edit profile.
    @PUT
    suspend fun ApiEditProfile(@Url url: String, @Body user: EditUser): Response<FeedBack>

    //Api Read All User.
    @GET
    suspend fun ApiReadAllUser(@Url url:String): Response<List<User>>

    //Api get info edit profile.
    @POST
    suspend fun ApiGetEditProfile(@Url url: String, @Body user: User): Response<User>

    //Api Read info User.
    @POST
    suspend fun ApiReadUser(@Url url: String, @Body user: User): Response<User>

    //Api change Password
    @PUT
    suspend fun ApiChangePassword(@Url url: String, @Body user: User): Response<FeedBack>

    //Api Create Property
    @POST
    suspend fun  ApiCreateProperty(@Url url:String, @Body property: Property): Response<FeedBack>

    //Api Read All Property
    @GET
    suspend fun ApiReadAllProperty(@Url url:String): Response<List<GetProperty>>

    //Api Read My Property
    @POST
    suspend fun ApiReadMyProperty(@Url url:String, @Body user: User): Response<List<GetProperty>>

    //Api Read Especific Property
    @POST
    suspend fun ApiReadProperty(@Url url:String, @Body property: Property): Response<User>

    //Api Login.
    @DELETE
    suspend fun ApiDeleteProperty(@Url url: String): Response<ResponseBody>

    //Api Update Property
    @PUT
    suspend fun ApiUpdateProperty(@Url url: String, @Body property: Property): Response<ResponseBody>

    //Api Get info edit property.
    @POST
    suspend fun ApiGetEditProperty(@Url url: String, @Body property: Property): Response<GetProperty>

    //Api Create Booking
    @POST
    suspend fun  ApiCreateBooking(@Url url:String, @Body booking: Booking): Response<FeedBack>

}
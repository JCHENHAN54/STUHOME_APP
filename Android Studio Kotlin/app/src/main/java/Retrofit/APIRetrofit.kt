package Retrofit

import model.FeedBack
import model.Property
import model.User
import retrofit2.Response
import retrofit2.http.Body
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
    suspend fun ApiEditProfile(@Url url: String, @Body user: User): Response<FeedBack>

    //Api get info edit profile.
    @POST
    suspend fun ApiGetEditProfile(@Url url: String, @Body user: User): Response<User>

    //Api change Password
    @PUT
    suspend fun ApiChangePassword(@Url url: String, @Body user: User): Response<FeedBack>

    //Api Create Property
    @POST
    suspend fun  ApiCreateProperty(@Url url:String, @Body property: Property): Response<FeedBack>
}
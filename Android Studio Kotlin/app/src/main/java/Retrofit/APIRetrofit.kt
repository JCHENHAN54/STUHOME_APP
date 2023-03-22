package Retrofit

import model.FeedBack
import model.User
import retrofit2.Response
import retrofit2.http.Body
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

}
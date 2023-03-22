package com.example.stuhome.Fragments

import Retrofit.APIRetrofit
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import com.example.stuhome.EditProfile
import com.example.stuhome.R
import com.example.stuhome.Login
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import model.User
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        //Aqui es Donde se pone las creaciones de variables y funciones. funciona igualmente
        // que en los activitys.

        val username: TextView = view.findViewById<TextView>(R.id.profile_username);
        val direction: TextView = view.findViewById<TextView>(R.id.profile_direction);
        val studies: TextView = view.findViewById<TextView>(R.id.profile_studies);
        val editProfileBtn: AppCompatButton =
            view.findViewById<AppCompatButton>(R.id.edit_profile_btn);

        //Pasar el username y el password que hemos introducido en Activity Login:
        val intent = requireActivity().intent;
        val loginEmail = intent.getStringExtra("email");
        val loginPass = intent.getStringExtra("password");

        val logOutBtn: AppCompatButton = view.findViewById(R.id.logOutBtn)

        //llamar funcion profileLogin para mostrar el informacion del perfil del usuario:
        profileLogin(loginEmail.toString(), loginPass.toString(), username, direction, studies);

        //A dar el button logout, ira a la pagina de signin.
        logOutBtn.setOnClickListener {
            val intent = Intent(activity, Login::class.java)
            startActivity(intent)
        }

        editProfileBtn.setOnClickListener {
            val intent = Intent(activity, EditProfile::class.java)
            intent.putExtra("loginEmail",loginEmail.toString())
            startActivity(intent)
        }

        // Inflate the layout for this fragment
        return view
    }

    fun profileLogin(loginEmail: String, loginPassword: String, username: TextView, direction: TextView, studies: TextView
    ) {
        //Codigo Retrofit:
        CoroutineScope(Dispatchers.IO).launch {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
            val conexion = Retrofit.Builder().baseUrl("http://10.0.2.2:8080/api/users/")
                .addConverterFactory(
                    GsonConverterFactory.create()
                ).client(client).build()
            var respuesta = conexion.create(APIRetrofit::class.java)
                .ApiProfileLogin(
                    "pLogin",
                    User(0, loginPassword, "", "", loginEmail, "", "", "")
                );
            withContext(Dispatchers.Main) {
                //SI el usuario ha creado su cuenta correctamente, pues ira a la pagina de home de applicacion.
                if (respuesta.isSuccessful) {
                    var user = respuesta.body();
                    //Pasar los infos del usuario al perfil.
                    username.setText("Hello, " + user?.name + " " + user?.apellido);
                    direction.setText("Direction: " + user?.direccion);
                    studies.setText("Studies: " + user?.studies);
                }
            }
        }
    }

}

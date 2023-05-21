package com.example.stuhome.Fragments

import Global.UserGlobal
import Retrofit.APIRetrofit
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Outline
import android.os.Bundle
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import com.example.stuhome.*
import com.example.stuhome.Activities.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import model.User
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Integer.min

class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        //Aqui es Donde se pone las creaciones de variables y funciones. funciona igualmente
        // que en los activitys.

        val username: TextView = view.findViewById(R.id.profile_username);
        val email: TextView = view.findViewById(R.id.profile_email);
        val profileImg: ImageView = view.findViewById(R.id.user_img);

        //Cambiar el Imagen como un Circulo:

        val logOutBtn: AppCompatButton = view.findViewById(R.id.logOutBtn)
        val editProfileBtn: AppCompatButton = view.findViewById(R.id.edit_profile_btn);
        val changePassBtn: AppCompatButton = view.findViewById(R.id.change_pass_btn);
        val createPropertyBtn :AppCompatButton = view.findViewById(R.id.addPropertyBtn);
        val myPropertyBtn :AppCompatButton = view.findViewById(R.id.myProperty_Btn);

        //Pasar el username y el password que hemos introducido en Activity Login:
        //val intent = requireActivity().intent;
        val loginEmail = UserGlobal.UserLoginInfo.userEmail
        val loginPass = UserGlobal.UserLoginInfo.password

        //llamar funcion profileLogin para mostrar el informacion del perfil del usuario:
        profileLogin(loginEmail, loginPass, username, email,profileImg);

        //Funcion Button:
        logOutBtn.setOnClickListener {
            val intent = Intent(activity, Login::class.java)
            startActivity(intent)
        }
        editProfileBtn.setOnClickListener {
            val intent = Intent(activity, EditProfile::class.java)
            startActivity(intent)
        }
        changePassBtn.setOnClickListener {
            val intent = Intent(activity, ChangePassword::class.java)
            startActivity(intent)
        }
        createPropertyBtn.setOnClickListener {
            val intent = Intent(activity, CreateProperty::class.java)
            startActivity(intent)
        }
        myPropertyBtn.setOnClickListener {
            val intent = Intent(activity, MyProperty::class.java)
            startActivity(intent)
        }

        // Inflate the layout for this fragment
        return view
    }

    fun profileLogin(loginEmail: String, loginPassword: String, username: TextView, email: TextView, profileImg: ImageView
    ) {
        //Codigo Retrofit:
        CoroutineScope(Dispatchers.IO).launch {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
            val conexion = Retrofit.Builder().baseUrl("http://10.0.2.2:8080/api/user/")
                .addConverterFactory(
                    GsonConverterFactory.create()
                ).client(client).build()
            var respuesta = conexion.create(APIRetrofit::class.java)
                    .ApiProfileLogin(
                    "pLogin",
                    User(0, loginPassword, "", "", loginEmail, "", "", "","")
                );
            withContext(Dispatchers.Main) {
                //SI el usuario ha creado su cuenta correctamente, pues ira a la pagina de home de applicacion.
                if (respuesta.isSuccessful) {
                    var user = respuesta.body();
                    //Pasar los infos del usuario al perfil.
                    username.setText(user?.name + " " + user?.surname);
                    email.setText(user?.email);
                    // Obtener la imagen desde la base de datos, y decodificar usando Base54.
                    if(user?.image != null){
                        val imageBytes = Base64.decode(user?.image, Base64.DEFAULT)
                        val bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
                        profileImg.setImageBitmap(bitmap)
                    }else{
                        profileImg.setImageResource(R.drawable.baseline_person_pin_24)
                    }
                }
            }
        }
    }

    fun onBackPressed() {
        // No hacemos nada
    }

}
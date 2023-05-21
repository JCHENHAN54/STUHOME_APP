package com.example.stuhome.Activities

import Global.UserGlobal
import Global.UserProfileImg
import Retrofit.APIRetrofit
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.bumptech.glide.Glide
import com.example.stuhome.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import model.EditUser
import model.User
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.InputStream

class EditProfile : AppCompatActivity() {

    private lateinit var ProfileImg: ImageView
    private lateinit var imageBytes: ByteArray

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        //EditText de la pantalla edit profile.
        val EditNameEt = findViewById<EditText>(R.id.edit_name)
        val EditSurnameEt = findViewById<EditText>(R.id.edit_surname)
        val EditDescriptionEt = findViewById<EditText>(R.id.edit_description)
        val EditDirectionEt = findViewById<EditText>(R.id.edit_direccion)
        val EditStudiesEt = findViewById<EditText>(R.id.edit_studies)

        //Buttones de edit profile.
        val saveChangesBtn: AppCompatButton = findViewById(R.id.profile_save_changesBtn)
        val backIcon = findViewById<ImageView>(R.id.back_icon)

        /* =============Configuration User Profile Image=============== */
        //Button cambiar imagen Usuario:
        val changeProfileImgBtn: AppCompatButton = findViewById(R.id.changeProfileimg)
        ProfileImg = findViewById(R.id.ProfileImg)
        imageBytes=byteArrayOf()

        changeProfileImgBtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            startActivityForResult(intent, 10)
        }
        /* =========================================================== */

        //Pasar textField que hemos introducido en Activity Profile:
        val loginEmail = UserGlobal.UserLoginInfo.userEmail

        //Poner los EditText por defecto.
        apiTextEditProfile(loginEmail,EditNameEt,EditSurnameEt,EditDescriptionEt,EditDirectionEt,EditStudiesEt,ProfileImg);

        backIcon.setOnClickListener{
            finish()
        }

        saveChangesBtn.setOnClickListener {
            //Pasar edittext a string:
            val EditName:String = EditNameEt.text.toString();
            val EditSurname:String = EditSurnameEt.text.toString();
            val EditDescription:String  = EditDescriptionEt.text.toString();
            val EditDirection:String = EditDirectionEt.text.toString();
            val EditStudies:String = EditStudiesEt.text.toString();

            //Debe rellenar todos los campos:
            if(EditName.isEmpty() || EditSurname.isEmpty() ||
                EditDescription.isEmpty() || EditDirection.isEmpty() || EditStudies.isEmpty()){
                val duration = Toast.LENGTH_SHORT
                val toast = Toast.makeText(applicationContext, "You have to complete all the fields.", duration)
                toast.show()
            }else{
                // Control error path de imagen de perfil usuario.
                val intent = Intent(this, ButtomBar::class.java)
                apiEditProfile(loginEmail.toString(),EditName,EditSurname,EditDescription,EditDirection,EditStudies,intent)
            }
        }
    }

    //Funcion Retrofit para actualizar los datos del usuario.
    fun apiEditProfile(loginEmail:String, EditName:String,
                       EditSurname:String, EditDescription:String, EditDirection:String, EditStudies:String,intent: Intent){
        //Codigo Retrofit:
        CoroutineScope(Dispatchers.IO).launch {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
            val conexion = Retrofit.Builder().baseUrl("http://10.0.2.2:8080/api/user/")
                .addConverterFactory(
                    GsonConverterFactory.create()).client(client).build()
            var respuesta = conexion.create(APIRetrofit::class.java)
                .ApiEditProfile("profile", EditUser(0,"",EditName,EditSurname,loginEmail,EditDescription,EditStudies,EditDirection,imageBytes));
            withContext(Dispatchers.Main) {
                //SI el usuario ha creado su cuenta correctamente, pues ira a la pagina de home de applicacion.
                if (respuesta.isSuccessful) {
                    val duration = Toast.LENGTH_SHORT
                    val toast = Toast.makeText(applicationContext, "Profile changed successfully!!", duration)
                    toast.show()
                    finish()
                }else {
                    val duration = Toast.LENGTH_SHORT
                    val toast = Toast.makeText(applicationContext, "An error has occurred", duration)
                    toast.show()
                }
            }
        }
    }

    //Funcion poner informacion de editar perfil por defecto.
    fun apiTextEditProfile(loginEmail:String,EditNameEt:EditText,EditSurnameEt:EditText,EditDescriptionEt:EditText,EditDirectionEt:EditText,EditStudiesEt:EditText,UserImage:ImageView){
        //Codigo Retrofit:
        CoroutineScope(Dispatchers.IO).launch {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
            val conexion = Retrofit.Builder().baseUrl("http://10.0.2.2:8080/api/user/")
                .addConverterFactory(
                    GsonConverterFactory.create()).client(client).build()
            var respuesta = conexion.create(APIRetrofit::class.java)
                .ApiGetEditProfile("GProfile", User(0,"","","",loginEmail,"","","",""));
            withContext(Dispatchers.Main) {
                //Poner informacion del usuario por defecto.
                if (respuesta.isSuccessful) {
                    var user = respuesta.body();
                    EditNameEt.setText(user?.name);
                    EditSurnameEt.setText(user?.surname);
                    EditDescriptionEt.setText(user?.description);
                    EditDirectionEt.setText(user?.description);
                    EditStudiesEt.setText(user?.studies);
                    //Cargar Imagen desde Mysql de usuario por defecto. //Decodificar el imagen bytesArray a bitmap.
                    val imageBytes = Base64.decode(user?.image, Base64.DEFAULT)
                    val bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
                    UserImage.setImageBitmap(bitmap)
                }else {
                    val duration = Toast.LENGTH_SHORT
                    val toast = Toast.makeText(applicationContext, "An error has occurred", duration)
                    toast.show()
                }
            }
        }
    }

    override fun onBackPressed() {
        // No hacemos nada
    }

    //Guardar imagen seleccionado en  imageView.
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 10 && resultCode == Activity.RESULT_OK && data != null) {
            val selectedImage: Uri? = data.data
            ProfileImg.setImageURI(selectedImage)

            //conventir el imagen seleccionado a un bytearray.
            val inputStream: InputStream? = selectedImage?.let { contentResolver.openInputStream(it) }
            val byteArrayOutputStream = ByteArrayOutputStream()
            if (inputStream != null) {
                val buffer = ByteArray(1024)
                var length: Int
                while (inputStream.read(buffer).also { length = it } != -1) {
                    byteArrayOutputStream.write(buffer, 0, length)
                }
                inputStream.close()
            }
            imageBytes = byteArrayOutputStream.toByteArray()
        }
    }

}
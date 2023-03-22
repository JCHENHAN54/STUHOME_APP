package com.example.stuhome

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val quotesApi=RetrofitHelper.getInstance().create(QuotesApi::class.java)
        //ejecutando coroutina
        GlobalScope.launch {
            val result=quotesApi.getQuotes()
            if(result!=null)
                //Comprobando Resultados
                Log.d("ayush:",result.body().toString())
        }

    }
}
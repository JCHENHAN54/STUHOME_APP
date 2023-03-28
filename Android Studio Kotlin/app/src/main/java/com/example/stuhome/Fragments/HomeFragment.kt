package com.example.stuhome.Fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import com.example.stuhome.AboutUs
import com.example.stuhome.R

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        //Aqui es Donde se pone las creaciones de variables y funciones. funciona igualmente
        // que en los activitys.

        val aboutUsIcon: ImageView = view.findViewById<ImageView>(R.id.aboutUs_icon)
        val startSeachBtn: AppCompatButton = view.findViewById<AppCompatButton>(R.id.start_searchBtn)

        //Pasar de un fragmento a otro.
        startSeachBtn.setOnClickListener{
            val fragmentManager = parentFragmentManager
            val fragmentTransition = fragmentManager.beginTransaction()
            fragmentTransition.replace(R.id.frame_layout,SearchFragment())
            fragmentTransition.commit()
        }

        aboutUsIcon.setOnClickListener{
            val intent = Intent(activity, AboutUs::class.java)
            startActivity(intent)
        }
        // Inflate the layout for this fragment
        return view
    }

    fun onBackPressed() {
        // No hacemos nada
    }

}
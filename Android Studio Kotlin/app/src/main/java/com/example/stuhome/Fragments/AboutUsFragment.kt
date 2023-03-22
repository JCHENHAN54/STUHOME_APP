package com.example.stuhome.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import com.example.stuhome.R


class AboutUsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_about_us, container, false)
        //Aqui es Donde se pone las creaciones de variables y funciones. funciona igualmente
        // que en los activitys.



        // Inflate the layout for this fragment
        return view
    }
}
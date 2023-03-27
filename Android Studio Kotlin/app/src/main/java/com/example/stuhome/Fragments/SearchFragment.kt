package com.example.stuhome.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.stuhome.R


class SearchFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_search, container, false)
        //Aqui es Donde se pone las creaciones de variables y funciones. funciona igualmente
        // que en los activitys.

        val searchET: EditText = view.findViewById<EditText>(R.id.search)

        searchET.requestFocus();

        // Inflate the layout for this fragment
        return view
    }

    fun onBackPressed() {
        // No hacemos nada
    }

}
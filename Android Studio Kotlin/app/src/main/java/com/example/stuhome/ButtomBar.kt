package com.example.stuhome

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.stuhome.Fragments.HomeFragment
import com.example.stuhome.Fragments.ProfileFragment
import com.example.stuhome.Fragments.SearchFragment
import com.example.stuhome.databinding.ActivityButtonbarBinding

class ButtomBar : AppCompatActivity() {

    private lateinit var binding: ActivityButtonbarBinding

    //Configuracion de binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityButtonbarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Cuando entra el activity de home, muestra primero el homeFragment.
        replaceFragment(HomeFragment())
        //Funcion setOnItenSelectedListener
        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                //Cuando esta clicando el icon de home.
                R.id.home -> replaceFragment(HomeFragment())
                //Cuando esta clicando el icon de aboutus
                R.id.search ->replaceFragment(SearchFragment())
                //Cuando esta clicando el icon de profile
                R.id.profile ->replaceFragment(ProfileFragment())
            }
            true;
        }
    }

    //Funcion para intercambiar los fragmentos.
    private fun replaceFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransition = fragmentManager.beginTransaction()
        fragmentTransition.replace(R.id.frame_layout,fragment)
        fragmentTransition.commit()
    }

    override fun onBackPressed() {
        // No hacemos nada
    }

}
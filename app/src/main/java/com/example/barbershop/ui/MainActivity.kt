package com.example.barbershop.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.barbershop.R
import com.example.barbershop.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var fragmentInicio: InicioFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fragmentInicio = InicioFragment()
        supportFragmentManager.beginTransaction().replace(R.id.containerMain, fragmentInicio).commit()

    }

}
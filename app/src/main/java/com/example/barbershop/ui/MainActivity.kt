package com.example.barbershop.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.barbershop.R
import com.example.barbershop.databinding.ActivityMainBinding
import com.example.barbershop.ui.addModule.AddCorteFragment
import com.example.barbershop.ui.entities.HomeAux
import com.example.barbershop.ui.hombreModule.HombreFragment
import com.example.barbershop.ui.mujerModule.MujerFragment
import com.example.barbershop.ui.productsModule.ProductsFragment
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    //Firebase
    private lateinit var mAuthListener: FirebaseAuth.AuthStateListener
    private var mFirebaseAuth: FirebaseAuth? = null

    private lateinit var mActiveFragment: Fragment
    private lateinit var mFragmentManager: FragmentManager

    //AuthUI
    private val authResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                Snackbar.make(binding.root, R.string.welcome_login_success, Snackbar.LENGTH_SHORT)
                    .show()
            } else {
                if (IdpResponse.fromResultIntent(result.data) == null) {
                    finish()
                }
            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupAuth()
        setupBottomNav()
    }

    private fun setupBottomNav() {
        mFragmentManager = supportFragmentManager

        val menFragment = HombreFragment()
        val womenFragment = MujerFragment()
        val productsFragment = ProductsFragment()
        val corteFragment = AddCorteFragment()

        mActiveFragment = menFragment
        mFragmentManager.beginTransaction()
            .add(R.id.hostFragment, corteFragment, AddCorteFragment::class.java.name)
            .hide(corteFragment)
            .commit()
        mFragmentManager.beginTransaction()
            .add(R.id.hostFragment, productsFragment, ProductsFragment::class.java.name)
            .hide(productsFragment)
            .commit()
        mFragmentManager.beginTransaction()
            .add(R.id.hostFragment, womenFragment, MujerFragment::class.java.name)
            .hide(womenFragment)
            .commit()
        mFragmentManager.beginTransaction()
            .add(R.id.hostFragment, menFragment, HombreFragment::class.java.name)
            .hide(menFragment)
            .commit()

        binding.bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_men -> {
                    mFragmentManager.beginTransaction().hide(mActiveFragment).show(menFragment)
                        .commit()
                    mActiveFragment = menFragment
                    (menFragment as HomeAux).goToTop()
                    true
                }
                R.id.navigation_women -> {
                    mFragmentManager.beginTransaction().hide(mActiveFragment).show(womenFragment)
                        .commit()
                    mActiveFragment = womenFragment
                    (menFragment as HomeAux).goToTop()
                    true
                }
                R.id.navigation_products -> {
                    mFragmentManager.beginTransaction().hide(mActiveFragment).show(productsFragment)
                        .commit()
                    mActiveFragment = productsFragment
                    (menFragment as HomeAux).goToTop()
                    true
                }
                R.id.action_add -> {
                    mFragmentManager.beginTransaction().hide(mActiveFragment).show(corteFragment)
                        .commit()
                    mActiveFragment = corteFragment
                    (menFragment as HomeAux).goToTop()
                    true
                }
                else -> false
            }
        }
    }

    private fun setupAuth() {
        mFirebaseAuth = FirebaseAuth.getInstance()
        mAuthListener = FirebaseAuth.AuthStateListener {
            val user = it.currentUser
            if (user == null) {
                authResult.launch(
                    AuthUI.getInstance().createSignInIntentBuilder()
                        .setIsSmartLockEnabled(false)
                        .setAvailableProviders(
                            Arrays.asList(
                                AuthUI.IdpConfig.EmailBuilder().build(),
                                AuthUI.IdpConfig.GoogleBuilder().build()
                            )
                        )
                        .build()
                )
            }
        }
    }

    override fun onResume() {
        super.onResume()
        mFirebaseAuth?.addAuthStateListener(mAuthListener)
    }

    override fun onPause() {
        super.onPause()
        mFirebaseAuth?.removeAuthStateListener(mAuthListener)
    }

}
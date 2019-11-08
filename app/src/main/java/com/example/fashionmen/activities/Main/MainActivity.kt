package com.example.fashionmen.activities.Main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.example.fashionmen.AppConfiguration
import com.example.fashionmen.AuthDataSingleton
import com.example.fashionmen.R
import com.example.fashionmen.fragments.catalog.FragmentCatalog
import com.example.fashionmen.fragments.cart.FragmentCart
import com.example.fashionmen.fragments.account.FragmentAccount
import com.example.fashionmen.fragments.account.FragmentLogin
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var configuracion: AppConfiguration

    companion object {
        const val FRAGMENTCATALOGO = "fragment_catalogo"
        const val FRAGMENTCESTA = "fragment_cesta"
        const val FRAGMENTPROFILE = "fragment_perfil"
        const val FRAGMENTLOGIN = "fragment_login"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        configuracion = AppConfiguration()

        bottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNavigationView.setOnNavigationItemSelectedListener {
           menuItem ->
            when(menuItem.itemId) {
                R.id.store -> {
                    val fragment: Fragment = FragmentCatalog()
                    changeFragment(fragment, FRAGMENTCATALOGO)
                    true
                }

                R.id.cesta -> {
                    val fragment: Fragment = FragmentCart()
                    changeFragment(fragment, FRAGMENTCESTA)
                    true
                }

                R.id.perfil -> {
                    if (AuthDataSingleton.loggedUser == null) {
                        val fragment: Fragment = FragmentLogin()
                        changeFragment(fragment, FRAGMENTLOGIN)
                    } else {
                        val fragment: Fragment = FragmentAccount()
                        changeFragment(fragment, FRAGMENTPROFILE)
                    }

                    true
                }
                else -> true
            }
        }
        bottomNavigationView.selectedItemId = R.id.store
    }

    public fun changeFragment(fragment: Fragment, nameFragment: String) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
        transaction.replace(R.id.nav_host_fragment, fragment)
        transaction.addToBackStack(nameFragment)
        transaction.commit()
    }
}

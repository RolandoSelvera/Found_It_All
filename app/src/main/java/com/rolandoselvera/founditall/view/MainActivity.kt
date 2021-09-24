package com.rolandoselvera.founditall.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import com.rolandoselvera.founditall.R
import com.rolandoselvera.founditall.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpNavController()
    }

    /**
     * Maneja evento de navegación cuando el usuario retrocede desde ActionBar.
     */
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    /**
     * Configura ActionBar para uso de Navigation.
     */
    private fun setUpNavController() {
        // Instancia de NavController desde NavHostFragment (contenedor):
        val navHostController = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostController.navController

        // Configura ActionBar para uso con NavController:
        setupActionBarWithNavController(this, navController)
    }
}
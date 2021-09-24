package com.rolandoselvera.founditall.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.rolandoselvera.founditall.R
import com.rolandoselvera.founditall.viewmodel.SplashViewModel

class SplashScreenActivity : AppCompatActivity() {

    // Instancia de SplashViewModel:
    private lateinit var splashViewModel: SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        // Oculta ActionBar:
        supportActionBar?.hide()

        observeSplashLiveData()
    }

    /**
     * Observa los cambios del componente livedata en SplashViewModel.
     * Luego, inicializa el delay y observa el estado actual.
     * Finalmente, ejecuta el intent y cierra SplashScreenActivity.
     */
    private fun observeSplashLiveData() {

        splashViewModel = ViewModelProvider(this)
            .get(SplashViewModel::class.java)

        splashViewModel.startDelaySplashScreen()

        splashViewModel.liveData.observe(this) {
            startActivity(Intent(this@SplashScreenActivity, MainActivity::class.java))
            finish()
        }
    }
}
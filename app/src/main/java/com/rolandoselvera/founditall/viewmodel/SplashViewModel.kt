package com.rolandoselvera.founditall.viewmodel

import androidx.lifecycle.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Esta clase ViewModel mantiene el estado de la UI si el usuario gira la pantalla
 * durante el inicio de la Activity. También, mantiene el estado actual ante cualquier
 * otro cambio de configuración en el dispositivo.
 *
 * Ver guía de ViewModel para más información en el enlace inferior.
 *
 * @see <a href="https://developer.android.com/topic/libraries/architecture/viewmodel?hl=es-419">Descripción general de ViewModel</a>
 */
class SplashViewModel : ViewModel() {
    var liveData: MutableLiveData<SplashViewModel> = MutableLiveData()

    /**
     * Corrutina para llamar en el UI Thread (hilo de interfaz) y observar su estado.
     */
    fun startDelaySplashScreen() {
        viewModelScope.launch {
            delay(2500)
            updateLiveData()
        }
    }

    private fun updateLiveData() {
        val splashViewModel = SplashViewModel()
        liveData.value = splashViewModel
    }
}
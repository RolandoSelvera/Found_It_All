package com.rolandoselvera.founditall.core.internet

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

/**
 * Clase para comprobar la conexión a internet del dispositivo.
 *
 * @param context
 */
class ConnectivityUtil(private val context: Context?) {
    /**
     * Método que comprueba si el dispositivo cuenta con conexión a internet.
     *
     * @return Devuelve 'true' si tiene conexión a internet y 'false' si no la tiene.
     */
    fun checkConnectivity(): Boolean {
        val connectivityManager =
            context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        // Devuelve una instancia de la conexión de la red activa:
        val isNetworkActive = connectivityManager.activeNetwork ?: return false

        // Obtiene "capacidades" o características de la red (capabilities):
        val capabilitiesNetwork =
            connectivityManager.getNetworkCapabilities(isNetworkActive) ?: return false

        return when {
            // Comprueba si el dispositivo está conectado por WiFi:
            capabilitiesNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true

            // Comprueba si el dispositivo está conectado por datos móviles:
            capabilitiesNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true

            else -> false
        }
    }
}
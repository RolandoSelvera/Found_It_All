package com.rolandoselvera.founditall.data.remote

import retrofit2.Response
import retrofit2.http.*

// Genera tu API KEY y reemplaza este valor. Consulta la documentación desde el sig. enlace
// para generarlo: https://tastedive.com/read/api
private const val API_KEY = "424981-FoundItA-DVB6HS1N"

interface ResultApiClient {

    /**
     * Petición GET a Endpoint.
     *
     * @return Devuelve todos los resultados de la consulta.
     */
    @GET("similar?info=1&k=$API_KEY")
    suspend fun getAllResults(
        @Query("q") search: String
    ): Response<ResultsSimilar>
}
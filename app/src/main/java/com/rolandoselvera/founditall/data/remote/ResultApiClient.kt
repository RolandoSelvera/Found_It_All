package com.rolandoselvera.founditall.data.remote

import retrofit2.Response
import retrofit2.http.*

private const val API_KEY = "417201-TestAppM-6O2E8KBC"

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
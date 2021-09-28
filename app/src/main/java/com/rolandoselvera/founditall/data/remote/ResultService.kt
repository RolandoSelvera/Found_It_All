package com.rolandoselvera.founditall.data.remote

import com.rolandoselvera.founditall.data.model.ResultModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Clase que realiza la llamada a Repository.
 */
class ResultService {

    private val retrofit = RetrofitHelper.getRetrofit()

    /**
     * Método que devuelve los resultados de la consulta a la API.
     */
    suspend fun getResults(): List<ResultModel> {
        return withContext(Dispatchers.IO) {
            val response = retrofit.create(ResultApiClient::class.java).getAllResults()
            response.body()?.results?.resultsEndpoint ?: emptyList()
        }
    }
}
package com.rolandoselvera.founditall.data.remote

import com.rolandoselvera.founditall.data.model.ResultModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Clase que realiza la llamada a Repository.
 */
class ResultService {

    private val retrofit = RetrofitHelper.getRetrofit()
    private val response = retrofit.create(ResultApiClient::class.java)

    /**
     * Método que devuelve los resultados de la consulta del nodo 'Results' a la API.
     *
     * @param search Término de búsqueda.
     */
    suspend fun getResults(search: String): List<ResultModel> {
        return withContext(Dispatchers.IO) {
            response.getAllResults(search).body()?.results?.resultsEndpoint ?: emptyList()
        }
    }

    /**
     * Método que devuelve los resultados de la consulta del nodo 'Info' a la API.
     *
     * @param search Término de búsqueda.
     */
    suspend fun getInfo(search: String): List<ResultsSearch> {
        return withContext(Dispatchers.IO) {
            response.getAllResults(search).body()?.results?.resultsInfo ?: emptyList()
        }
    }
}
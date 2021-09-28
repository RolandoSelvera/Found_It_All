package com.rolandoselvera.founditall.data.repository

import com.rolandoselvera.founditall.data.model.ResultModel
import com.rolandoselvera.founditall.data.model.ResultProvider
import com.rolandoselvera.founditall.data.remote.ResultService

class ResultRepository {

    private val api = ResultService()

    suspend fun getAllResults(): List<ResultModel> {
        val response = api.getResults()
        ResultProvider.results = response
        return response
    }
}
package com.rolandoselvera.founditall.data.repository

import com.rolandoselvera.founditall.data.model.ResultModel
import com.rolandoselvera.founditall.data.model.ResultProvider
import com.rolandoselvera.founditall.data.remote.ResultService
import com.rolandoselvera.founditall.data.remote.ResultsSearch

class ResultRepository {

    private val api = ResultService()

    suspend fun getAllResults(search: String): List<ResultModel> {
        val response = api.getResults(search)
        ResultProvider.results = response
        return response
    }

    suspend fun getInfo(search: String): List<ResultsSearch> {
        val response = api.getInfo(search)
        ResultProvider.resultInfo = response
        return response
    }
}
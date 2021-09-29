package com.rolandoselvera.founditall.domain

import com.rolandoselvera.founditall.data.model.ResultModel
import com.rolandoselvera.founditall.data.remote.ResultsSearch
import com.rolandoselvera.founditall.data.repository.ResultRepository

class GetResultsUseCase {

    private val repository = ResultRepository()

    suspend fun invokeResults(search: String): List<ResultModel> =
        repository.getAllResults(search)

    suspend fun invokeInfo(search: String): List<ResultsSearch> =
        repository.getInfo(search)
}
package com.rolandoselvera.founditall.domain

import com.rolandoselvera.founditall.data.model.ResultModel
import com.rolandoselvera.founditall.data.repository.ResultRepository

class GetResultsUseCase {

    private val repository = ResultRepository()

    suspend operator fun invoke(): List<ResultModel> =
        repository.getAllResults()
}
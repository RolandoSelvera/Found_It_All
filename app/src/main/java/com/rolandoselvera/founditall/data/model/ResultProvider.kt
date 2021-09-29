package com.rolandoselvera.founditall.data.model

import com.rolandoselvera.founditall.data.remote.ResultsSearch

class ResultProvider {
    companion object {
        var results: List<ResultModel> = emptyList()
        var resultInfo: List<ResultsSearch> = emptyList()
    }
}
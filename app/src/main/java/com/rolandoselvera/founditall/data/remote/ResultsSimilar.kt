package com.rolandoselvera.founditall.data.remote

import com.google.gson.annotations.SerializedName
import com.rolandoselvera.founditall.data.model.ResultModel

data class ResultsSimilar(
    @SerializedName("Similar")
    val results: ResultsSearch?
)

data class ResultsInfo(
    @SerializedName("Info")
    val resultsInfo: ResultsSearch?,
    @SerializedName("Name")
    val name: String?,
    @SerializedName("Type")
    val type: String?
)

data class ResultsSearch(
    @SerializedName("Results")
    val resultsEndpoint: List<ResultModel>?
)
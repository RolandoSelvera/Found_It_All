package com.rolandoselvera.founditall.data.remote

import com.google.gson.annotations.SerializedName
import com.rolandoselvera.founditall.data.model.ResultModel

/**
 * Obtiene lista de resultados de la búsqueda en nodo 'Results'.
 */
data class ResultsSimilar(
    @SerializedName("Similar")
    val results: ResultsSearch?
)

// Obtiene lista resultados de nodo 'Info' y 'Results':
data class ResultsSearch(
    @SerializedName("Results")
    val resultsEndpoint: List<ResultModel>?,
    @SerializedName("Info")
    val resultsInfo: List<ResultsSearch>?,
    @SerializedName("Name")
    val name: String?,
    @SerializedName("Type")
    val type: String?,
    @SerializedName("wTeaser")
    val wikiTeaser: String,
    @SerializedName("yUrl")
    val youTubeUrl: String,
    @SerializedName("yID")
    val youTubeId: String
)
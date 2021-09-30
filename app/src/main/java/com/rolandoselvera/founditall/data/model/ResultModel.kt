package com.rolandoselvera.founditall.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ResultModel(
    val id: Int,
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
) : Serializable
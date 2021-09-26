package com.rolandoselvera.founditall.model

data class Result(
    val id: Int = 0,
    val name: String,
    val type: String,
    val wikiTeaser: String,
    val youTubeUrl: String,
    val youTubeId: String
)
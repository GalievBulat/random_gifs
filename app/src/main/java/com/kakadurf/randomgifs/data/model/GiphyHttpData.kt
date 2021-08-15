package com.kakadurf.randomgifs.data.model

import com.google.gson.annotations.SerializedName

data class GiphyHttpData(
    @SerializedName("type")
    val type: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("url")
    val url: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("images")
    val innerImages: GiphyHttpImages
)

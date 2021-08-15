package com.kakadurf.randomgifs.data.model

import com.google.gson.annotations.SerializedName

data class GiphyImage(
    @SerializedName("url")
    val downloadUrl: String
)

package com.kakadurf.randomgifs.data.model

import com.google.gson.annotations.SerializedName

data class GiphyHttpResponse(
    @SerializedName("data")
    val data: GiphyHttpData
)

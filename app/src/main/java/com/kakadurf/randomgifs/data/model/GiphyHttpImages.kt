package com.kakadurf.randomgifs.data.model

import com.google.gson.annotations.SerializedName

data class GiphyHttpImages(

    @SerializedName("downsized")
    val gifImage: GiphyImage
)

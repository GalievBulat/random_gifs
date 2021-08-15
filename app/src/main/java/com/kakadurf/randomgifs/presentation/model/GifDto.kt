package com.kakadurf.randomgifs.presentation.model

data class GifDto(
    val id: String,
    val gifUrl: String,
    val title: String,
    val media: ByteArray? = null,
    var isChecked: Boolean = false
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as GifDto

        if (id != other.id) return false
        if (isChecked != other.isChecked) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + isChecked.hashCode()
        return result
    }
}

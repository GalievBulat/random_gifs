package com.kakadurf.randomgifs.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cache")
data class CachedGif(
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB, name = "gif_data")
    val gifData: ByteArray,
    @ColumnInfo(name = "url")
    val url: String,
    @ColumnInfo(name = "title")
    val title: String,
    @PrimaryKey val id: String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CachedGif

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}

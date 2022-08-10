package uz.unidev.dictionary.data.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

/**
 *  Created by Nurlibay Koshkinbaev on 30/07/2022 23:54
 */

@Entity(tableName = "dictionary")
@Parcelize
data class WordEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val english: String,
    val type: String,
    val transcript: String,
    val uzbek: String,
    val countable: String?,
    var is_favourite: Int,
    var is_favourite_uzb: Int?
) : Parcelable
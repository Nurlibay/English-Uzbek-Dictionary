package uz.unidev.dictionary.data.dao

import androidx.room.Dao
import androidx.room.Query
import uz.unidev.dictionary.data.entity.WordEntity

/**
 *  Created by Nurlibay Koshkinbaev on 31/07/2022 00:13
 */

@Dao
interface UzEngDao : BaseDao<WordEntity> {

    @Query("SELECT * FROM dictionary")
    suspend fun getAllWords(): List<WordEntity>

    @Query("SELECT * FROM dictionary WHERE uzbek LIKE '%'|| :query || '%'")
    suspend fun getSearchResult(query: String): List<WordEntity>

    @Query("SELECT * FROM dictionary WHERE is_favourite_uzb = 1")
    suspend fun getAllBookmarks(): List<WordEntity>

    @Query("UPDATE dictionary SET is_favourite_uzb = 0 WHERE is_favourite_uzb = 1")
    suspend fun deleteAllBookmarks()
}
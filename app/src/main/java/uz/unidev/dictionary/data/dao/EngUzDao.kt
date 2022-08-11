package uz.unidev.dictionary.data.dao

import android.database.Cursor
import androidx.room.Dao
import androidx.room.Query
import uz.unidev.dictionary.data.entity.WordEntity

/**
 *  Created by Nurlibay Koshkinbaev on 31/07/2022 00:13
 */

@Dao
interface EngUzDao : BaseDao<WordEntity> {

    @Query("SELECT * FROM dictionary")
    fun getAllWords(): Cursor

    @Query("SELECT * FROM dictionary WHERE english LIKE '%'|| :query || '%'")
    fun getSearchResult(query: String): Cursor

    @Query("SELECT * FROM dictionary WHERE is_favourite = 1")
    fun getAllBookmarks(): Cursor

    @Query("UPDATE dictionary SET is_favourite = 0 WHERE is_favourite = 1")
    fun deleteAllBookmarks()
}
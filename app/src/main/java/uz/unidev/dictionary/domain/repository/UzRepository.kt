package uz.unidev.dictionary.domain.repository

import android.database.Cursor
import uz.unidev.dictionary.data.entity.WordEntity

interface UzRepository {

    fun getAllWords(): Cursor

    fun getSearchResult(query: String): Cursor

    suspend fun update(wordEntity: WordEntity)

    fun getAllBookmarks(): Cursor

    fun deleteAllBookmarks()

}
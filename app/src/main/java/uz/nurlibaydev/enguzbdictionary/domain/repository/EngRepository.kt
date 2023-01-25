package uz.nurlibaydev.enguzbdictionary.domain.repository

import android.database.Cursor
import uz.nurlibaydev.enguzbdictionary.data.entity.WordEntity

interface EngRepository {

    fun getAllWords(): Cursor

    fun getSearchResult(query: String): Cursor

    suspend fun update(wordEntity: WordEntity)

    fun getAllBookmarks(): Cursor

    fun deleteAllBookmarks()
}
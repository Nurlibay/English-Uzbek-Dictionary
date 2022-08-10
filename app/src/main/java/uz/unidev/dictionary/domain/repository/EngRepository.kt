package uz.unidev.dictionary.domain.repository

import uz.unidev.dictionary.data.entity.WordEntity

interface EngRepository {

    suspend fun getAllWords(): List<WordEntity>

    suspend fun getSearchResult(query: String): List<WordEntity>

    suspend fun update(wordEntity: WordEntity)

    suspend fun getAllBookmarks(): List<WordEntity>

    suspend fun deleteAllBookmarks()
}
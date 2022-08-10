package uz.unidev.dictionary.domain.repository.impl

import uz.unidev.dictionary.data.dao.EngUzDao
import uz.unidev.dictionary.data.entity.WordEntity
import uz.unidev.dictionary.domain.repository.EngRepository

/**
 *  Created by Nurlibay Koshkinbaev on 31/07/2022 18:06
 */

class EngRepositoryImpl constructor(
    private val dao: EngUzDao
) : EngRepository {

    override suspend fun getAllWords(): List<WordEntity> {
        return dao.getAllWords()
    }

    override suspend fun getSearchResult(query: String): List<WordEntity> {
        return dao.getSearchResult(query)
    }

    override suspend fun update(wordEntity: WordEntity) {
        dao.update(wordEntity)
    }

    override suspend fun getAllBookmarks(): List<WordEntity> {
        return dao.getAllBookmarks()
    }

    override suspend fun deleteAllBookmarks() {
        dao.deleteAllBookmarks()
    }

}
package uz.unidev.dictionary.domain.repository.impl

import uz.unidev.dictionary.data.dao.EngUzDao
import uz.unidev.dictionary.data.dao.UzEngDao
import uz.unidev.dictionary.data.entity.WordEntity
import uz.unidev.dictionary.domain.repository.UzRepository

/**
 *  Created by Nurlibay Koshkinbaev on 05/08/2022 15:27
 */

class UzRepositoryImpl(
    private val uzDao: UzEngDao
): UzRepository {

    override suspend fun getAllWords(): List<WordEntity> {
        return uzDao.getAllWords()
    }

    override suspend fun getSearchResult(query: String): List<WordEntity> {
        return uzDao.getSearchResult(query)
    }

    override suspend fun update(wordEntity: WordEntity) {
        uzDao.update(wordEntity)
    }

    override suspend fun getAllBookmarks(): List<WordEntity> {
        return uzDao.getAllBookmarks()
    }

    override suspend fun deleteAllBookmarks() {
        uzDao.deleteAllBookmarks()
    }
}
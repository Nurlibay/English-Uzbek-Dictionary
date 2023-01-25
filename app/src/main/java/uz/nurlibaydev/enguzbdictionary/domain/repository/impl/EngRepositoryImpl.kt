package uz.nurlibaydev.enguzbdictionary.domain.repository.impl

import android.database.Cursor
import uz.nurlibaydev.enguzbdictionary.data.dao.EngUzDao
import uz.nurlibaydev.enguzbdictionary.data.entity.WordEntity
import uz.nurlibaydev.enguzbdictionary.domain.repository.EngRepository

/**
 *  Created by Nurlibay Koshkinbaev on 31/07/2022 18:06
 */

class EngRepositoryImpl constructor(
    private val dao: EngUzDao
) : EngRepository {

    override fun getAllWords(): Cursor {
        return dao.getAllWords()
    }

    override fun getSearchResult(query: String): Cursor {
        return dao.getSearchResult(query)
    }

    override suspend fun update(wordEntity: WordEntity) {
        dao.update(wordEntity)
    }

    override fun getAllBookmarks(): Cursor {
        return dao.getAllBookmarks()
    }

    override fun deleteAllBookmarks() {
        dao.deleteAllBookmarks()
    }

}
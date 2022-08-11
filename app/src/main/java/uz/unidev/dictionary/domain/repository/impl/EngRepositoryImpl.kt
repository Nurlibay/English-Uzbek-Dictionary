package uz.unidev.dictionary.domain.repository.impl

import android.database.Cursor
import androidx.paging.PagingSource
import kotlinx.coroutines.flow.Flow
import uz.unidev.dictionary.data.dao.EngUzDao
import uz.unidev.dictionary.data.entity.WordEntity
import uz.unidev.dictionary.domain.repository.EngRepository

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
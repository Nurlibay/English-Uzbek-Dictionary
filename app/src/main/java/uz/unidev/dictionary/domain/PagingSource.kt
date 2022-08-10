//package uz.unidev.dictionary.domain
//
//import androidx.paging.PagingSource
//import androidx.paging.PagingState
//import uz.unidev.dictionary.data.entity.WordEntity
//import uz.unidev.dictionary.domain.repository.EngRepository
//
///**
// *  Created by Nurlibay Koshkinbaev on 09/08/2022 19:18
// */
//
//class PagingSource(private val engRepository: EngRepository) : PagingSource<Int, WordEntity>() {
//
//    private companion object {
//        const val INITIAL_PAGE_INDEX = 0
//    }
//
//    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, WordEntity> {
//        val position = params.key ?: INITIAL_PAGE_INDEX
//        val randomPosts = engRepository.getAllWords()
//        return LoadResult.Page(
//            data = randomPosts,
//            prevKey = if (position == INITIAL_PAGE_INDEX) null else position - 1,
//            nextKey = if (randomPosts.isEmpty()) null else position + 1
//        )
//    }
//
//    override fun getRefreshKey(state: PagingState<Int, WordEntity>): Int? = null
//}
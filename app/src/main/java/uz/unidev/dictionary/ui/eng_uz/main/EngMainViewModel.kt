package uz.unidev.dictionary.ui.eng_uz.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import uz.unidev.dictionary.data.entity.WordEntity
import uz.unidev.dictionary.domain.repository.EngRepository
import uz.unidev.dictionary.utils.Resource

/**
 *  Created by Nurlibay Koshkinbaev on 31/07/2022 18:16
 */

class EngMainViewModel(private val engRepository: EngRepository) : ViewModel() {

    private var _word: MutableLiveData<Resource<List<WordEntity>>> = MutableLiveData()
    val word get() = _word

    fun getAllWords() {
        _word.postValue(Resource.loading())
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _word.postValue(
                    Resource.success(engRepository.getAllWords())
                )
            } catch (e: Exception) {
                _word.postValue(Resource.error(e.localizedMessage))
            }
        }
    }

    private var _searchedWord: MutableLiveData<Resource<List<WordEntity>>> = MutableLiveData()
    val searchedWord get() = _searchedWord

    fun getSearchedWords(query: String) {
        _searchedWord.postValue(Resource.loading())
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _searchedWord.postValue(Resource.success(engRepository.getSearchResult(query)))
            } catch (e: Exception) {
                _searchedWord.postValue(Resource.error(e.localizedMessage))
            }
        }
    }
}
package uz.nurlibaydev.enguzbdictionary.ui.eng_uz.main

import android.database.Cursor
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import uz.nurlibaydev.enguzbdictionary.domain.repository.EngRepository
import uz.nurlibaydev.enguzbdictionary.utils.Resource

/**
 *  Created by Nurlibay Koshkinbaev on 31/07/2022 18:16
 */

class EngMainViewModel(private val engRepository: EngRepository) : ViewModel() {

    private var _word: MutableLiveData<Resource<Cursor>> = MutableLiveData()
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

    private var _searchedWord: MutableLiveData<Resource<Cursor>> = MutableLiveData()
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
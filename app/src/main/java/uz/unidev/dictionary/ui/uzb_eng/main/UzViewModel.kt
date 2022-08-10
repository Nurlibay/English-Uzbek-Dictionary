package uz.unidev.dictionary.ui.uzb_eng.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import uz.unidev.dictionary.data.entity.WordEntity
import uz.unidev.dictionary.domain.repository.UzRepository
import uz.unidev.dictionary.utils.Resource

/**
 *  Created by Nurlibay Koshkinbaev on 05/08/2022 15:26
 */

class UzViewModel(private val uzRepository: UzRepository) : ViewModel() {

    private var _word: MutableLiveData<Resource<List<WordEntity>>> = MutableLiveData()
    val word get() = _word

    fun getAllWords() {
        _word.postValue(Resource.loading())
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _word.postValue(Resource.success(uzRepository.getAllWords()))
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
                _searchedWord.postValue(Resource.success(uzRepository.getSearchResult(query)))
            } catch (e: Exception) {
                _searchedWord.postValue(Resource.error(e.localizedMessage))
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("VVV", "onCleared")
    }

}
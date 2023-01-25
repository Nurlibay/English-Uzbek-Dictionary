package uz.nurlibaydev.enguzbdictionary.ui.eng_uz.bookmark

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import uz.nurlibaydev.enguzbdictionary.domain.repository.EngRepository
import uz.nurlibaydev.enguzbdictionary.utils.Resource

/**
 *  Created by Nurlibay Koshkinbaev on 03/08/2022 11:55
 */

class BookmarkViewModel(private val engRepository: EngRepository) : ViewModel() {

    private var _bookmark: MutableLiveData<Resource<Cursor>> = MutableLiveData()
    val bookmark: LiveData<Resource<Cursor>> = _bookmark

    fun getAllBookmarks() {
        _bookmark.value = Resource.loading()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _bookmark.postValue(Resource.success(engRepository.getAllBookmarks()))
            } catch (e: Exception) {
                _bookmark.postValue(Resource.error(e.localizedMessage))
            }
        }
    }

    fun deleteAllBookmarks() {
        viewModelScope.launch(Dispatchers.IO) {
            engRepository.deleteAllBookmarks()
        }
    }
}
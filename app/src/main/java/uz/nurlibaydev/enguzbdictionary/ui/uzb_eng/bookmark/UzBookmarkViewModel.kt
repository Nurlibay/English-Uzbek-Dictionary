package uz.nurlibaydev.enguzbdictionary.ui.uzb_eng.bookmark

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import uz.nurlibaydev.enguzbdictionary.domain.repository.UzRepository
import uz.nurlibaydev.enguzbdictionary.utils.Resource

/**
 *  Created by Nurlibay Koshkinbaev on 06/08/2022 16:51
 */

class UzBookmarkViewModel(private val uzRepository: UzRepository): ViewModel() {

    private var _bookmark: MutableLiveData<Resource<Cursor>> = MutableLiveData()
    val bookmark: LiveData<Resource<Cursor>> = _bookmark

    fun getAllBookmarks() {
        _bookmark.value = Resource.loading()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _bookmark.postValue(Resource.success(uzRepository.getAllBookmarks()))
            } catch (e: Exception) {
                _bookmark.postValue(Resource.error(e.localizedMessage))
            }
        }
    }

    fun deleteAllBookmarks() {
        viewModelScope.launch(Dispatchers.IO) {
            uzRepository.deleteAllBookmarks()
        }
    }

}
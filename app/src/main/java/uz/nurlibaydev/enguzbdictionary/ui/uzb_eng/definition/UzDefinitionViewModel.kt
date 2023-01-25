package uz.nurlibaydev.enguzbdictionary.ui.uzb_eng.definition

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import uz.nurlibaydev.enguzbdictionary.data.entity.WordEntity
import uz.nurlibaydev.enguzbdictionary.domain.repository.UzRepository

/**
 *  Created by Nurlibay Koshkinbaev on 06/08/2022 17:07
 */

class UzDefinitionViewModel(private val repository: UzRepository) : ViewModel() {

    fun update(wordEntity: WordEntity) {
        viewModelScope.launch {
            if (wordEntity.is_favourite_uzb == null || wordEntity.is_favourite_uzb == 0) {
                wordEntity.is_favourite_uzb = 0
                wordEntity.is_favourite_uzb = 1 - wordEntity.is_favourite_uzb!!
                repository.update(wordEntity)
            } else {
                wordEntity.is_favourite_uzb = 1 - wordEntity.is_favourite_uzb!!
                repository.update(wordEntity)
            }
        }
    }

}
package uz.unidev.dictionary.ui.eng_uz.definition

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import uz.unidev.dictionary.data.entity.WordEntity
import uz.unidev.dictionary.domain.repository.EngRepository

/**
 *  Created by Nurlibay Koshkinbaev on 03/08/2022 12:00
 */

class DefinitionViewModel(private val engRepository: EngRepository): ViewModel() {

    fun update(wordEntity: WordEntity) {
        viewModelScope.launch {
            wordEntity.is_favourite = 1 - wordEntity.is_favourite
            engRepository.update(wordEntity)
        }
    }
}
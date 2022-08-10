package uz.unidev.dictionary.ui.uzb_eng.definition

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import uz.unidev.dictionary.R
import uz.unidev.dictionary.data.entity.WordEntity
import uz.unidev.dictionary.databinding.BottomSheetUzDefinitionBinding
import java.util.*

/**
 *  Created by Nurlibay Koshkinbaev on 06/08/2022 14:42
 */

class UzDefinitionBottomSheet : BottomSheetDialogFragment() {

    private lateinit var binding: BottomSheetUzDefinitionBinding
    private val viewModel: UzDefinitionViewModel by viewModel()
    private lateinit var mTTS: TextToSpeech
    private lateinit var wordEntity: WordEntity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottom_sheet_uz_definition, container, false)
    }

    private var listener: ((WordEntity?) -> Unit?)? = null
    fun setOnBookmarkIconClickListener(block: (WordEntity?) -> Unit) {
        this.listener = block
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val data = requireArguments().getParcelable<WordEntity>("data")!!

        wordEntity = WordEntity(
            data.id,
            data.english,
            data.type,
            data.transcript,
            data.uzbek,
            data.countable,
            data.is_favourite,
            data.is_favourite_uzb
        )

        binding = BottomSheetUzDefinitionBinding.bind(view)

        binding.engWord.text = wordEntity.english
        binding.transcription.text = "[ " + wordEntity.transcript + " ]"
        binding.uzbWord.text = wordEntity.uzbek
        binding.type.text = wordEntity.type + "."

        if (wordEntity.countable != null) {
            binding.countable.visibility = View.VISIBLE
            binding.countable.text = wordEntity.countable
        } else {
            binding.countable.visibility = View.GONE
        }

        binding.ivBack.setOnClickListener {
            requireDialog().dismiss()
        }

        binding.ivVolume.setOnClickListener {
            speak(wordEntity.english)
        }

        if (wordEntity.is_favourite_uzb == 0 || wordEntity.is_favourite_uzb == null) {
            binding.ivBookmark.setImageResource(R.drawable.ic_bookmark_border)
        } else {
            binding.ivBookmark.setImageResource(R.drawable.ic_red_bookmark)
        }

        binding.ivBookmark.setOnClickListener {
            listener?.invoke(wordEntity)
            viewModel.update(wordEntity)
            if (wordEntity.is_favourite_uzb == 0 || wordEntity.is_favourite_uzb == null) {
                binding.ivBookmark.setImageResource(R.drawable.ic_bookmark_border)
            } else {
                binding.ivBookmark.setImageResource(R.drawable.ic_red_bookmark)
            }
        }

        binding.ivShare.setOnClickListener {

            val shareContent = "Word: " + data.english + "\n" +
                    "Type: " + data.type + "\n" +
                    "Transcription: " + data.transcript + "\n" +
                    "Means: " + data.uzbek + "\n" +
                    "Countable: " + data.type + "\n"

            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, shareContent)
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }

    }

    private fun speak(english: String) {
        mTTS = TextToSpeech(requireContext()) {
            if (it == TextToSpeech.SUCCESS) {
                mTTS.language = Locale.ENGLISH
                // mTTS.setSpeechRate(0.1f)
                mTTS.speak(english, TextToSpeech.QUEUE_FLUSH, null, null)
            }
        }
    }

}
package uz.unidev.dictionary.ui.eng_uz.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.speech.RecognizerIntent
import android.speech.tts.TextToSpeech
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber
import uz.unidev.dictionary.R
import uz.unidev.dictionary.data.entity.WordEntity
import uz.unidev.dictionary.databinding.FragmentEngMainBinding
import uz.unidev.dictionary.ui.eng_uz.definition.DefinitionBottomSheet
import uz.unidev.dictionary.utils.Constants.SPEECH_REQUEST_CODE
import uz.unidev.dictionary.utils.Extensions.addVerticalDivider
import uz.unidev.dictionary.utils.Extensions.showMessage
import uz.unidev.dictionary.utils.ResourceState
import java.util.*

/**
 *  Created by Nurlibay Koshkinbaev on 31/07/2022 01:25
 */

class EngMainFragment : Fragment(R.layout.fragment_eng_main) {

    private lateinit var binding: FragmentEngMainBinding
    private val viewModel: EngMainViewModel by viewModel()
    private lateinit var navController: NavController
    private val handler: Handler by lazy { Handler(Looper.getMainLooper()) }
    private var adapter = EngWordAdapter()
    private lateinit var mTTS: TextToSpeech

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentEngMainBinding.bind(view)
        navController = Navigation.findNavController(view)
        init()
        viewModel.getAllWords()
        setupObserver()

        adapter.onVolumeIconClickListener { word ->
            speak(word)
        }

        adapter.onItemClickListener {
            val bottomSheet = DefinitionBottomSheet()
            bottomSheet.setOnBookmarkIconClickListener {
                viewModel.getAllWords()
            }
            val bundle = Bundle()
            bundle.putParcelable("data", it)
            bottomSheet.arguments = bundle
            bottomSheet.show(childFragmentManager, "BOTTOM_SHEET")
        }

        binding.searchEditText.doOnTextChanged { text, _, _, _ ->
            val query = text.toString().lowercase()
            adapter.query = text.toString()
            filterWithQuery(query)
            toggleImageView(query)
        }

        binding.clearSearchQuery.setOnClickListener {
            binding.searchEditText.setText("")
            binding.noSearchResultsFoundText.visibility = View.INVISIBLE
        }

        binding.voiceSearchQuery.setOnClickListener {
            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
                putExtra(
                    RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                    RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
                )
            }
            startActivityForResult(intent, SPEECH_REQUEST_CODE)
        }

        binding.ivBookmarks.setOnClickListener {
            navController.navigate(EngMainFragmentDirections.actionMainFragmentToBookmarkFragment())
        }
    }

    private fun init() {
        binding.rvWords.adapter = adapter
        binding.rvWords.addVerticalDivider(requireContext())
        binding.rvWords.setHasFixedSize(true)
    }

    private fun setupObserver() {
        viewModel.word.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                ResourceState.LOADING -> {}
                ResourceState.SUCCESS -> {
                    adapter.submitCursor(it.data!!)
                }
                ResourceState.ERROR -> {
                    showMessage(it.message.toString())
                    Timber.tag("TTT").d(it.message.toString())
                }
                else -> {}
            }
        })
    }

    private fun setupObserveSearchedList() {
        viewModel.searchedWord.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                ResourceState.LOADING -> {}
                ResourceState.SUCCESS -> {
                    adapter.submitCursor(it.data!!)
                    if (it.data.count == 0) {
                        binding.noSearchResultsFoundText.visibility = View.VISIBLE
                    } else {
                        binding.noSearchResultsFoundText.visibility = View.GONE
                    }
                }
                ResourceState.ERROR -> {
                    showMessage("Something Wrong!")
                }
                else -> {}
            }
        })
    }

    private fun speak(word: WordEntity) {
        mTTS = TextToSpeech(requireContext()) {
            if (it == TextToSpeech.SUCCESS) {
                mTTS.language = Locale.ENGLISH
                // mTTS.setSpeechRate(0.1f)
                mTTS.speak(word.english, TextToSpeech.QUEUE_FLUSH, null, null)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == SPEECH_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val spokenText: String? =
                data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS).let { results ->
                    results?.get(0)
                }
            binding.searchEditText.setText(spokenText)
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun filterWithQuery(query: String) {
        if (query.isNotEmpty()) {
            handler.postDelayed({
                viewModel.getSearchedWords(query)
            }, 300)
            setupObserveSearchedList()
        } else {
            viewModel.getAllWords()
        }
    }

    private fun toggleImageView(query: String) {
        if (query.isNotEmpty()) {
            binding.clearSearchQuery.visibility = View.VISIBLE
            binding.voiceSearchQuery.visibility = View.INVISIBLE
        } else if (query.isEmpty()) {
            binding.clearSearchQuery.visibility = View.INVISIBLE
            binding.voiceSearchQuery.visibility = View.VISIBLE
        }
    }

    override fun onPause() {
        super.onPause()
        binding.searchEditText.setText("")
        toggleImageView("")
    }
}

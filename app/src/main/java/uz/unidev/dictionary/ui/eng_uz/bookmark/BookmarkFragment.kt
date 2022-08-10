package uz.unidev.dictionary.ui.eng_uz.bookmark

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.koin.androidx.viewmodel.ext.android.viewModel
import uz.unidev.dictionary.R
import uz.unidev.dictionary.data.entity.WordEntity
import uz.unidev.dictionary.databinding.FragmentBookmarkBinding
import uz.unidev.dictionary.ui.eng_uz.definition.DefinitionBottomSheet
import uz.unidev.dictionary.utils.Extensions.addVerticalDivider
import uz.unidev.dictionary.utils.Extensions.showMessage
import uz.unidev.dictionary.utils.ResourceState
import java.util.*

/**
 *  Created by Nurlibay Koshkinbaev on 03/08/2022 01:50
 */

class BookmarkFragment : Fragment(R.layout.fragment_bookmark) {

    private lateinit var binding: FragmentBookmarkBinding
    private val adapter = BookmarkAdapter()
    private val viewModel: BookmarkViewModel by viewModel()
    private lateinit var navController: NavController
    private lateinit var mTTS: TextToSpeech

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentBookmarkBinding.bind(view)
        navController = Navigation.findNavController(view)
        attachAdapter()
        viewModel.getAllBookmarks()
        setupObserver()

        adapter.onItemClickListener {
            val bottomSheet = DefinitionBottomSheet()
            bottomSheet.setOnBookmarkIconClickListener {
                viewModel.getAllBookmarks()
            }
            val bundle = Bundle()
            bundle.putParcelable("data", it)
            bottomSheet.arguments = bundle
            bottomSheet.show(requireActivity().supportFragmentManager, "BOTTOM_SHEET")

        }

        binding.ivBack.setOnClickListener {
            navigateUp()
        }

        adapter.onVolumeIconClickListener { word ->
            speak(word)
        }

        binding.ivDelete.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext(), R.style.MaterialAlertDialog_rounded)
                .setTitle(resources.getString(R.string.title_eng))
                .setMessage(resources.getString(R.string.message_eng))
                .setNegativeButton(resources.getString(R.string.no)) { dialog, _ ->
                    dialog.dismiss()
                }
                .setPositiveButton(resources.getString(R.string.ok)) { dialog, _ ->
                    viewModel.deleteAllBookmarks()
                    adapter.submitList(mutableListOf())
                    binding.noFavourites.visibility = View.VISIBLE
                    dialog.dismiss()
                }
                .create()
                .show()
        }
    }

    private fun attachAdapter() {
        binding.rvBookmark.adapter = adapter
        binding.rvBookmark.addVerticalDivider(requireContext())
    }

    private fun navigateUp() {
        findNavController().popBackStack()
    }

    private fun setupObserver() {
        viewModel.bookmark.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                ResourceState.LOADING -> {}
                ResourceState.SUCCESS -> {
                    adapter.submitList(it.data!!)
                    if (it.data.isEmpty()) {
                        binding.noFavourites.visibility = View.VISIBLE
                    } else {
                        binding.noFavourites.visibility = View.GONE
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

    override fun onResume() {
        super.onResume()
        viewModel.getAllBookmarks()
    }
}
package uz.unidev.dictionary.ui.uzb_eng.bookmark

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.koin.androidx.viewmodel.ext.android.viewModel
import uz.unidev.dictionary.R
import uz.unidev.dictionary.data.entity.WordEntity
import uz.unidev.dictionary.databinding.FragmentUzBookmarkBinding
import uz.unidev.dictionary.ui.uzb_eng.definition.UzDefinitionBottomSheet
import uz.unidev.dictionary.utils.Extensions.addVerticalDivider
import uz.unidev.dictionary.utils.Extensions.showMessage
import uz.unidev.dictionary.utils.ResourceState
import java.util.*

/**
 *  Created by Nurlibay Koshkinbaev on 06/08/2022 16:49
 */

class UzBookmarkFragment : Fragment(R.layout.fragment_uz_bookmark) {

    private lateinit var binding: FragmentUzBookmarkBinding
    private val adapter = UzBookmarkAdapter()
    private val viewModel: UzBookmarkViewModel by viewModel()
    private lateinit var navController: NavController
    private lateinit var mTTS: TextToSpeech

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentUzBookmarkBinding.bind(view)
        navController = Navigation.findNavController(view)
        attachAdapter()
        viewModel.getAllBookmarks()
        setupObserver()

        adapter.onItemClickListener {
            val bottomSheet = UzDefinitionBottomSheet()
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
                .setTitle(resources.getString(R.string.title_uz))
                .setMessage(resources.getString(R.string.message_uz))
                .setNegativeButton(resources.getString(R.string.no_uz)) { dialog, _ ->
                    dialog.dismiss()
                }
                .setPositiveButton(resources.getString(R.string.ok_uz)) { dialog, _ ->
                    viewModel.deleteAllBookmarks()
                    viewModel.getAllBookmarks()
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
        viewModel.bookmark.observe(viewLifecycleOwner) {
            when (it.status) {
                ResourceState.LOADING -> {}
                ResourceState.SUCCESS -> {
                    adapter.submitCursor(it.data!!)
                    if (it.data.count == 0) {
                        binding.noFavourites.visibility = View.VISIBLE
                    } else {
                        binding.noFavourites.visibility = View.GONE
                    }
                }
                ResourceState.ERROR -> {
                    showMessage(it.message.toString())
                }
                else -> {}
            }
        }
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
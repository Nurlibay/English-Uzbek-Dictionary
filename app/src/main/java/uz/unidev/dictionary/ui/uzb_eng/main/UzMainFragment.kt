package uz.unidev.dictionary.ui.uzb_eng.main

import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import org.koin.androidx.viewmodel.ext.android.viewModel
import uz.unidev.dictionary.R
import uz.unidev.dictionary.databinding.FragmentUzMainBinding
import uz.unidev.dictionary.ui.eng_uz.definition.DefinitionBottomSheet
import uz.unidev.dictionary.ui.uzb_eng.definition.UzDefinitionBottomSheet
import uz.unidev.dictionary.utils.Extensions.addVerticalDivider
import uz.unidev.dictionary.utils.Extensions.showMessage
import uz.unidev.dictionary.utils.ResourceState

/**
 *  Created by Nurlibay Koshkinbaev on 05/08/2022 15:24
 */

class UzMainFragment : Fragment(R.layout.fragment_uz_main) {

    private lateinit var binding: FragmentUzMainBinding
    private val viewModel: UzViewModel by viewModel()
    private lateinit var navController: NavController

    private var adapter = UzWordAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentUzMainBinding.bind(view)
        navController = Navigation.findNavController(view)
        init()
        viewModel.getAllWords()
        setupObserver()

        adapter.onItemClickListener {
            val bottomSheet = UzDefinitionBottomSheet()
            val bundle = Bundle()
            bundle.putParcelable("data", it)
            bottomSheet.arguments = bundle
            bottomSheet.show(requireActivity().supportFragmentManager, "BOTTOM_SHEET")
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

        binding.ivBookmarks.setOnClickListener {
            navController.navigate(UzMainFragmentDirections.actionUzMainFragmentToUzBookmarkFragment())
        }
    }

    private fun init() {
        binding.rvWords.adapter = adapter
        binding.rvWords.addVerticalDivider(requireContext())
    }

    private fun setupObserver() {
        viewModel.word.observe(viewLifecycleOwner) {
            when (it.status) {
                ResourceState.LOADING -> {}
                ResourceState.SUCCESS -> {
                    adapter.submitList(it.data!!)
                }
                ResourceState.ERROR -> {
                    showMessage("Something Wrong!")
                }
                else -> {}
            }
        }
    }

    private fun setupObserveSearchedList() {
        viewModel.searchedWord.observe(viewLifecycleOwner) {
            when (it.status) {
                ResourceState.LOADING -> {}
                ResourceState.SUCCESS -> {
                    adapter.submitList(it.data!!)
                    if (it.data.isEmpty()) {
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
        }
    }

    private fun filterWithQuery(query: String) {
        if (query.isNotEmpty()) {
            viewModel.getSearchedWords(query)
            setupObserveSearchedList()
        } else {
            viewModel.getAllWords()
        }
    }

    private fun toggleImageView(query: String) {
        if (query.isNotEmpty()) {
            binding.clearSearchQuery.visibility = View.VISIBLE
        } else if (query.isEmpty()) {
            binding.clearSearchQuery.visibility = View.INVISIBLE
        }
    }

}
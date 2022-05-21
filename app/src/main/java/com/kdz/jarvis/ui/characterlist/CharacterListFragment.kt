package com.kdz.jarvis.ui.characterlist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.map
import com.google.android.material.snackbar.Snackbar
import com.kdz.jarvis.R
import com.kdz.jarvis.databinding.FragmentCharacterListBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CharacterListFragment : Fragment() {

    val viewModel: CharacterListViewModel by viewModels()

    lateinit var binding: FragmentCharacterListBinding

    private val loadingSnackbar by lazy {
        Snackbar.make(requireView(), R.string.character_list_loading, Snackbar.LENGTH_INDEFINITE)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCharacterListBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel

        observePagedCharacters()
        observePagedLoadingState()

        binding.characterListView.setOnCharacterClickListener { character ->
            val navAction =
                CharacterListFragmentDirections.actionCharacterListFragmentToCharacterDetailsFragment(
                    character.id,
                    character.name
                )

            findNavController().navigate(navAction)
        }
    }

    private fun observePagedCharacters() = lifecycleScope.launch {
        viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.characters.collectLatest {
                binding.characterListView.submitData(it)
            }
        }
    }

    private fun observePagedLoadingState() = lifecycleScope.launch {
        viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            binding.characterListView.characterAdapter?.loadStateFlow?.collect(::onCharacterLoadStatesReceived)
        }
    }

    private fun onCharacterLoadStatesReceived(loadStates: CombinedLoadStates) {
        if (loadStates.append is LoadState.Loading) {
            loadingSnackbar.show()
        } else {
            loadingSnackbar.dismiss()
        }

        binding.loadingView.isGone = loadStates.refresh !is LoadState.Loading
    }

}
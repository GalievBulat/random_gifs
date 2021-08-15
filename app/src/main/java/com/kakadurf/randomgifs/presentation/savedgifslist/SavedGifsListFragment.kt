package com.kakadurf.randomgifs.presentation.savedgifslist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.kakadurf.randomgifs.R
import com.kakadurf.randomgifs.databinding.FragmentSavedGifsListBinding
import com.kakadurf.randomgifs.presentation.AppDelegate
import com.kakadurf.randomgifs.presentation.model.GifDto
import com.kakadurf.randomgifs.presentation.randomgifslist.GifListAdapter
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SavedGifsListFragment : Fragment(R.layout.fragment_saved_gifs_list) {
    private val binding: FragmentSavedGifsListBinding by viewBinding()
    private val viewModel: SavedGifsListFragmentViewModel by viewModels {
        AppDelegate.viewModelFactory
    }
    private var adapter: GifListAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.gifListNavigationButton.setOnClickListener {
            findNavController().navigate(R.id.gifListFragment)
        }
        val set = mutableSetOf<GifDto>()
        adapter = GifListAdapter { gif ->
            viewLifecycleOwner.lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.RESUMED) {
                    viewModel.deleteFromSaved(gif.id).takeIf {
                        it > 0
                    }?.let {
                        set.remove(gif)
                        adapter?.submitList(set.toList())
                    }
                }
            }
        }
        binding.rvGifsList.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.savedGifsList.collect { gifList ->
                    if (gifList?.isEmpty() == true) {
                        binding.clHelp.visibility = View.VISIBLE
                    } else
                        gifList?.forEach {
                            set.add(it)
                            adapter?.submitList(set.toList())
                        }
                }
            }
        }
    }
}

package com.kakadurf.randomgifs.presentation.randomgifslist

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import by.kirich1409.viewbindingdelegate.viewBinding
import com.kakadurf.randomgifs.R
import com.kakadurf.randomgifs.databinding.FragmentGifListBinding
import com.kakadurf.randomgifs.presentation.AppDelegate
import com.kakadurf.randomgifs.presentation.model.GifDto
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class GifListFragment : Fragment(R.layout.fragment_gif_list) {
    private val binding: FragmentGifListBinding by viewBinding()
    private val viewModel: GifListFragmentViewModel by viewModels {
        AppDelegate.viewModelFactory
    }
    private val onErrorAction: (IOException) -> Unit = {
        Toast.makeText(
            activity,
            when (it) {
                is UnknownHostException -> resources.getString(R.string.error_unknown_host)
                is SocketTimeoutException -> resources.getString(R.string.error_timeout)
                else -> it.localizedMessage
            },
            Toast.LENGTH_LONG
        ).show()
    }
    private var adapter: GifListAdapter? = null
    private var gifList: List<GifDto> = listOf()
    private var requestJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = GifListAdapter { gif ->
            viewLifecycleOwner.lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.RESUMED) {
                    viewModel.saveGif(gif)
                    val list = gifList.toList()
                    val i = list.indexOf(gif)
                    list[i].isChecked = true
                    adapter?.notifyItemChanged(i)
                }
            }
        }
        binding.rvGifsList.adapter = adapter
        getGifsSet()
        binding.swipeLayoutMain.setOnRefreshListener {
            getGifsSet {
                binding.swipeLayoutMain.isRefreshing = false
            }
        }
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        inflater.inflate(R.menu.action_menu, menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.action_update) {
            getGifsSet()
            true
        } else super.onOptionsItemSelected(item)
    }

    private fun getGifsSet(onEach: () -> Unit = { }) {
        requestJob?.cancel()
        requestJob = viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                binding.progressBarGifsRequest.visibility = View.VISIBLE
                binding.rvGifsList.isVisible = false
                viewModel.getRandomGifs(10, onErrorAction).collect { gifs ->
                    if (gifs != null && gifs.isNotEmpty()) {
                        gifList = gifs
                        adapter?.submitList(gifList.toList())
                        onEach()
                        binding.progressBarGifsRequest.visibility = View.GONE
                        binding.rvGifsList.isVisible = true
                    }
                }
            }
        }
    }
}

package com.example.coinalculator.ui.favorite.presently

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coinalculator.databinding.FragmentNotificationsBinding
import com.example.coinalculator.ui.favorite.presently.adapter.FavoriteAdapter
import com.example.coinalculator.ui.favorite.presently.states.FavoriteStates
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavoriteFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null
    private val binding get() = _binding!!

    private val scope = CoroutineScope(Dispatchers.IO)
    private val adapter = FavoriteAdapter { }

    private val viewModel by viewModels<FavoriteViewModel> {
        FeatureServiceLocator.provideFavoriteViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.adapter = adapter

        scope.launch {
            viewModel.loadCoins()
        }

        viewLifecycleOwner.lifecycleScope.launch {

            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.favoriteState.collect { state ->
                    when {
                        state.isLoading -> showLoading()

                        state.hasError -> {
                            showError()
                            viewModel.errorShown()
                        }

                        else -> withContext(Dispatchers.Main) {
                            showList(state.favoriteList)
                        }
                    }

                }
            }
        }
    }

    private fun showLoading() {
        binding.recyclerView.visibility = View.GONE
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun showError() {
        Toast.makeText(
            requireContext(),
            "Error wile loading data",
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun showList(favoriteList: List<FavoriteStates>) {
        adapter.setData(favoriteList)
        binding.recyclerView.visibility = View.VISIBLE
        binding.progressBar.visibility = View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}
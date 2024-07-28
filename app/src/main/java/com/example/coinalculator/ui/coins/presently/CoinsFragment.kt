package com.example.coinalculator.ui.coins.presently

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
import com.example.coinalculator.databinding.FragmentDashboardBinding
import com.example.coinalculator.ui.coins.presently.adapter.CoinsAdapter
import com.example.coinalculator.ui.coins.presently.model.CoinState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class CoinsFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    private val scope = CoroutineScope(Dispatchers.IO)
    private var searchJob: Job? = null
    private val adapter = CoinsAdapter()
    var arg: String = ""

    private val viewModel by viewModels<CoinViewModel> {
        FeatureServiceLocator.provideCoinsViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
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
                viewModel.coinState.collect { state ->
                    when {
                        state.isLoading -> showLoading()

                        state.hasError -> {
                            showError()
                            viewModel.errorShown()
                        }

                        state.filter -> withContext(Dispatchers.Main) {
                            showFoundCoins()
                        }

                        else -> withContext(Dispatchers.Main) {
                            showList(state.coinsList)
                        }
                    }
                }
            }
        }



        binding.search.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                searchJob?.cancel()
                searchJob = scope.launch {
                     arg = binding.search.text.toString()
                    viewModel.searchCoin(arg)

                    withContext(Dispatchers.Main) {
                        if (viewModel.filter.value.isEmpty()) {
                            viewModel.stateFilter = false
                            Toast.makeText(requireActivity(), "Nothing found for your request", Toast.LENGTH_SHORT).show()
                        } else {
                            viewModel.stateFilter = true
                        }
                    }
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }


    private fun showLoading() {
        binding.recyclerView.visibility = View.GONE
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun showList(list: List<CoinState>) {
        adapter.setData(list)
        binding.recyclerView.visibility = View.VISIBLE
        binding.progressBar.visibility = View.GONE
    }

    private fun showFoundCoins() {
        viewModel.filter.onEach {
            adapter.setData(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    override fun onDestroyView() {
        scope.cancel()
        super.onDestroyView()
        _binding = null
    }

    private fun showError() {
        Toast.makeText(
            requireContext(),
            "Error wile loading data",
            Toast.LENGTH_SHORT
        ).show()
    }
}
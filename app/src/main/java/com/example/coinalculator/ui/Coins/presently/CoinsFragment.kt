package com.example.coinalculator.ui.Coins.presently

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coinalculator.databinding.FragmentDashboardBinding
import com.example.coinalculator.ui.Coins.ServiceLocator
import com.example.coinalculator.ui.Coins.presently.adapter.CoinsAdapter
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

    private val viewModel: CoinsViewModel by viewModels(
        factoryProducer = { ServiceLocator.provideViewModel() }
    )
    private val scope = CoroutineScope(Dispatchers.IO)
    private var searchJob: Job? = null
    private val adapter = CoinsAdapter()


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

        viewModel.coin.onEach {
            adapter.setData(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        binding.search.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                searchJob?.cancel()
                searchJob = scope.launch {
                    viewModel.searchCoin(binding.search.text.toString())
                    withContext(Dispatchers.Main) {
                        if (viewModel.filter.value.isEmpty()) {
                            Toast.makeText(requireActivity(), "null", Toast.LENGTH_SHORT).show()
                        } else {
                            viewModel.filter.onEach {
                                adapter.setData(it)
                            }.launchIn(viewLifecycleOwner.lifecycleScope)
                        }
                    }
                }
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    override fun onDestroyView() {
        scope.cancel()
        super.onDestroyView()
        _binding = null
    }
}
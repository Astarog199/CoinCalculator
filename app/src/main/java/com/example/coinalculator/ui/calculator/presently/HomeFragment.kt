package com.example.coinalculator.ui.calculator.presently

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
import kotlinx.coroutines.launch
import com.example.coinalculator.databinding.FragmentHomeBinding
import com.example.coinalculator.ui.calculator.ServiceLocator
import com.example.coinalculator.ui.calculator.presently.adapter.HomeAdapter
import com.example.coinalculator.ui.calculator.presently.states.CoinCalState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val adapter = HomeAdapter{state, s2 -> onTextChange(state, s2)}
    private val scope = CoroutineScope(Dispatchers.IO)

    private val viewModel: CalculatorViewModel by viewModels(
        factoryProducer = {ServiceLocator.provideViewModel()}
    )

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.loadItems()
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.items.collect { state ->
                    when{
                        state.isLoading -> showLoading()

                        else -> {
                            showList(state.coinsList)
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

    private fun showList(coinsList: List<CoinCalState>) {
        adapter.setData(coinsList)
        binding.recyclerView.visibility = View.VISIBLE
        binding.progressBar.visibility = View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onTextChange(arg: Float, arg2: Float){
        scope.launch {
            viewModel.changeVolume(arg * arg2)
        }
    }

    private fun showError() {
        Toast.makeText(
            requireContext(),
            "Error wile loading data",
            Toast.LENGTH_SHORT
        ).show()
    }
}
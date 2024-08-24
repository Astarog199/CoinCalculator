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

class CalculatorFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val adapter = HomeAdapter{arg, item-> onTextChange(arg, item)}


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

        viewModel.loadItems()

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.items.collect { state ->
                    when{
                        state.isLoading -> showLoading()

                        state.hasError -> {
                            showError()
                            viewModel.errorShown()
                        }

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

    private fun onTextChange(arg: Float, item: CoinCalState){
        if (item.name == "rub") {
            viewModel.changeVolume(arg / item.price)
        }else{
            viewModel.changeVolume(arg * item.price)
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
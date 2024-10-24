package com.example.coinalculator.ui.calculator.presently

import android.content.Context
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
import com.example.coinalculator.App
import kotlinx.coroutines.launch
import com.example.coinalculator.databinding.FragmentCalculatorBinding
import com.example.coinalculator.ui.calculator.presently.adapter.CalculatorAdapter
import com.example.coinalculator.ui.calculator.presently.states.CoinCalState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import java.lang.Exception
import javax.inject.Inject

class CalculatorFragment : Fragment() {

    private var _binding: FragmentCalculatorBinding? = null
    private val binding get() = _binding!!
    private val scope = CoroutineScope(Dispatchers.Main)
    private val adapter = CalculatorAdapter(
        textChange = { arg, item -> onTextChange(arg, item) },
        showError = { exception -> showErrorTextChange(exception) }
    )

    @Inject
    lateinit var viewModelFactory: CalculatorViewModelFactory

    private val viewModel: CalculatorViewModel by viewModels { viewModelFactory }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity?.applicationContext as App).appComponent
            .calculatorFragmentFactory()
            .create()
            .inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentCalculatorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.adapter = adapter

        viewModel.loadItems()

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.items.collect { state ->
                    when {
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
        binding.instruction.visibility = View.GONE
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun showList(coinsList: List<CoinCalState>) {
        adapter.setData(coinsList)
        binding.recyclerView.visibility = View.VISIBLE
        binding.progressBar.visibility = View.GONE

        if (viewModel.showInstructions()){
            binding.instruction.visibility = View.VISIBLE
        } else {
            binding.instruction.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        scope.cancel()
    }

    private fun onTextChange(arg: Float, item: CoinCalState){
        when {
            item.symbol == "rub" -> viewModel.changeVolume(arg / item.price)
            else -> viewModel.changeVolume(arg * item.price)
        }
    }

    private fun showErrorTextChange(exception: Exception) {
        scope.launch {
            when(exception) {
               is NumberFormatException -> {
                    Toast.makeText(
                        requireActivity(),
                        "Error: enter number in edit Text",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                else -> {
                    Toast.makeText(
                        requireContext(),
                        "Error: ${exception.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
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
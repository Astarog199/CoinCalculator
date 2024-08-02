package com.example.coinalculator.ui.card.presentation.card

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.coinalculator.databinding.FragmentCoinCardBinding
import com.example.coinalculator.ui.card.presentation.FeatureServiceLocator
import com.example.coinalculator.ui.card.presentation.card.states.CoinCardStates
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class CoinCardFragment : Fragment() {

    private var _binding: FragmentCoinCardBinding? = null
    private val binding get() = _binding!!
    var itemId = ""

    private val viewModel: CoinCardViewModel by viewModels<CoinCardViewModel>{
        FeatureServiceLocator.provideCoinCardViewModelFactory(itemId)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCoinCardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments.let {
            itemId = it?.getString("item").toString()
        }

        viewModel.loadCoinCard()
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.state.collect{ state ->
                    when{
                        state.isLoading -> renderLoading()

                        state.hasError -> {showError()
                                            viewModel.clearError()
                        }
                        else-> renderCoinCard(state.coin)
                    }

                }
            }
        }
    }

    private fun renderCoinCard(coin: CoinCardStates) {
        binding.name.text = coin.name
        binding.name.visibility = View.VISIBLE

        binding.price.text = coin.price
        binding.price.visibility = View.VISIBLE


        binding.progress.visibility = View.GONE
    }

    private fun renderLoading() {
        binding.name.visibility = View.GONE
        binding.price.visibility = View.GONE
        binding.progress.visibility = View.VISIBLE
    }


    override fun onDestroy() {
        super.onDestroy()
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
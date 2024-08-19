package com.example.coinalculator.ui.coins.presently.card

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
import com.example.coinalculator.ui.coins.presently.card.states.CoinCardStates
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jetbrains.skia.Color

class CoinCardFragment : Fragment() {

    private val scope = CoroutineScope(Dispatchers.IO)
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

                        state.hasError -> {
                            showError()
                            viewModel.clearError()
                        }

                        else-> {
                            renderCoinCard(state.coin)
                        }
                    }
                }
            }
        }


        binding.addToFavorite.setOnClickListener {
            scope.launch {
                viewModel.changeFavoriteState()
            }
        }
    }

    private fun renderCoinCard(coin: CoinCardStates) {
        binding.name.text = coin.name
        binding.name.visibility = View.VISIBLE

        binding.PriceChange.text = formatChange24h(coin.change24h)
        binding.PriceChange.visibility = View.VISIBLE

        binding.priceText.visibility = View.VISIBLE
        binding.price.text = coin.price
        binding.price.visibility = View.VISIBLE

        binding.marketText.visibility = View.VISIBLE
        binding.market.text = coin.market
        binding.market.visibility = View.VISIBLE

        binding.progress.visibility = View.GONE

        if (coin.isFavorite){
            binding.addToFavorite.text =  "remove to favorite"
        }else{
            binding.addToFavorite.text =  "add to favorite"
        }
    }

    private fun formatChange24h(value: Float) : String {
        if (value < 0f){
            binding.PriceChange.setTextColor(Color.RED)
        }else{
            binding.PriceChange.setTextColor(Color.GREEN)
        }

        return String.format("%.1f", value)+ " %"
    }

    private fun renderLoading() {
        binding.name.visibility = View.GONE
        binding.priceText.visibility = View.GONE
        binding.price.visibility = View.GONE
        binding.PriceChange.visibility = View.GONE
        binding.marketText.visibility = View.GONE
        binding.market.visibility = View.GONE
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
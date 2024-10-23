package com.example.coinalculator.ui.coins.presently.card

import android.content.Context
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
import coil.load
import com.example.coinalculator.App
import com.example.coinalculator.R
import com.example.coinalculator.databinding.FragmentCoinCardBinding
import com.example.coinalculator.ui.coins.presently.card.states.CoinCardStates
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.Locale
import javax.inject.Inject

class CoinCardFragment : Fragment() {

    private val scope = CoroutineScope(Dispatchers.IO)
    private var _binding: FragmentCoinCardBinding? = null
    private val binding get() = _binding!!
    private var coinName = ""

    @Inject
    lateinit var viewModelFactory: CoinCardViewModelFactory

    private val viewModel: CoinCardViewModel by viewModels{
        viewModelFactory
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        (activity?.applicationContext as App).appComponent
            .coinCardFragmentFactory()
            .create()
            .inject(this)

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
            coinName = it?.getString("item").toString()
        }

        viewModel.loadCoinCard(coinName)
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


        binding.addToAlculator.setOnClickListener {
            scope.launch {
                viewModel.changeFavoriteState()
            }
        }
    }

    private fun renderCoinCard(coin: CoinCardStates) {
        binding.name.text = coin.name
        binding.name.visibility = View.VISIBLE

        binding.priceChange.text = formatChange24h(coin.priceChange24h, coin.pricePercentageChange24h)
        binding.priceChange.visibility = View.VISIBLE

        binding.price.text = formatNumber(coin.price)
        binding.price.visibility = View.VISIBLE

        binding.image.load(coin.image)
        binding.image.visibility = View.VISIBLE

        binding.marketCapRankValue.text = coin.marketCapRank.toString()
        binding.marketCapRankValue.visibility = View.VISIBLE
        binding.marketCapRankText.visibility = View.VISIBLE

        binding.circulatingSupplyValue.text = formatSupplyValue(coin.circulatingSupply)
        binding.circulatingSupplyText.visibility = View.VISIBLE
        binding.circulatingSupplyText.visibility = View.VISIBLE

        binding.totalSupplyValue.text = formatSupplyValue(coin.totalSupply)
        binding.totalSupplyText.visibility = View.VISIBLE
        binding.totalSupplyValue.visibility = View.VISIBLE

        binding.maxSupplyValue.text = formatSupplyValue(coin.maxSupply)
        binding.maxSupply.visibility = View.VISIBLE
        binding.maxSupplyValue.visibility = View.VISIBLE

        binding.marketCapValue.text = formatNumber(coin.marketCap)
        binding.marketCapValue.visibility = View.VISIBLE
        binding.marketCapText.visibility = View.VISIBLE

        binding.totalVolumeValue.text = formatNumber(coin.totalVolume)
        binding.totalVolumeText.visibility = View.VISIBLE
        binding.totalVolumeValue.visibility = View.VISIBLE

        binding.dailyRangeValue.text = stringDailyRange(coin.low24h, coin.high24h)
        binding.dailyRangeValue.visibility = View.VISIBLE
        binding.dailyRangeText.visibility = View.VISIBLE

        binding.progress.visibility = View.GONE

        if (coin.isFavorite){
            binding.addToAlculator.text = getString(R.string.remove_to_calculator)
        }else{
            binding.addToAlculator.text =  getString(R.string.add_to_calculator)
        }
    }

    private fun stringDailyRange(low24h: Float, high24h:Float): String {
        val formatter = NumberFormat.getCurrencyInstance(Locale.US)
        formatter.maximumFractionDigits = 1
        formatter.minimumFractionDigits = 0

       return " ${formatter.format(low24h)} - ${formatter.format(high24h)} "
    }

    private fun formatSupplyValue(value: Float) : String {
        return if (value != 0f) {
            formatNumber(value).replace("$", "")
        } else{
            "∞"
        }
    }

    private fun formatChange24h(value: Float, valuePercent: Float) : String {
        if (value < 0f){
            binding.priceChange.setTextColor(resources.getColor(R.color.red))
        }else{
            binding.priceChange.setTextColor(resources.getColor(R.color.green))
        }

        return String.format("%.2f",  value) +" $ · " + String.format("%.2f", valuePercent)+ " %"
    }

    private fun formatNumber(value: Number) : String {
        var v = value.toLong()

        var sizeOfNumber = 0
        while (v > 10000){
            v /= 10
            sizeOfNumber++
        }
        val formatter = NumberFormat.getCurrencyInstance(Locale.US)
        formatter.maximumFractionDigits = 1
        formatter.minimumFractionDigits = 1

        return when  {
            sizeOfNumber >= 9 -> formatter.format(value.toLong()/1000000000) + " T"

            sizeOfNumber >= 6 -> formatter.format(value.toLong()/1000000) + " B"

            else -> formatter.format(value)
        }
    }

    private fun renderLoading() {
        binding.image.visibility = View.GONE
        binding.name.visibility = View.GONE
        binding.price.visibility = View.GONE
        binding.priceChange.visibility = View.GONE
        binding.marketCapRankValue.visibility = View.GONE
        binding.marketCapRankText.visibility = View.GONE
        binding.totalSupplyValue.visibility = View.GONE
        binding.totalSupplyValue.visibility = View.GONE
        binding.maxSupply.visibility = View.GONE
        binding.maxSupplyValue.visibility = View.GONE
        binding.marketCapText.visibility = View.GONE
        binding.marketCapValue.visibility = View.GONE
        binding.totalVolumeText.visibility = View.GONE
        binding.totalVolumeValue.visibility = View.GONE
        binding.dailyRangeText.visibility = View.GONE

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
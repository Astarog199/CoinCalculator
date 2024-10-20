package com.example.coinalculator.ui.calculator.presently.adapter

import android.text.Editable
import android.text.TextWatcher
import androidx.recyclerview.widget.RecyclerView
import com.example.coinalculator.databinding.CalculatorItemBinding
import com.example.coinalculator.ui.calculator.presently.states.CoinCalState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CalculatorHolder(
    private val binding: CalculatorItemBinding,
    private val textChange: (Float, CoinCalState) -> Unit,
    private val showError: (Exception) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    private val scope = CoroutineScope(Dispatchers.IO)
    private var searchJob: Job? = null
    private var stateTextTracking = true

    fun bind(item: CoinCalState?) {
        with(binding) {
            text.text = item?.symbol
            editText.setText("")
            editText.setText(getCost(item))
            editText.hint = getCost(item)


            editText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    searchJob?.cancel()
                    searchJob = scope.launch {
                        delay(500)

                        val arg = editText.text
                        try {
                            if (arg.isNotEmpty() && stateTextTracking) {
                                item?.let {
                                    textChange(arg.toString().toFloat(), item)
                                }
                            }
                        } catch (e: Exception) {
                            println("Error: $e")
                            showError(e)
                        }
                    }
                }

                override fun afterTextChanged(s: Editable?) {
                    stateTextTracking = true
                }
            })
        }
    }

    private fun getCost(item: CoinCalState?): String {
        stateTextTracking = false

        return when {
            item?.price == null || item.price == 0f -> "0.0"
            item.getPriceValue() == 0f -> "0.0"
            item.getPriceValue() < 0.0001 -> "< 0.0001"
            item.getPriceValue() > 9999999999 -> "> 9999999999"
            item.getPriceValue() > 999999999 -> String.format("%.1f", item?.getPriceValue()).replace(',', '.')
            item.symbol == "rub" || item.symbol == "usd" -> String.format("%.2f", item.price * item.value).replace(',', '.')
            else -> {
                String.format("%.4f", item?.getPriceValue()).replace(',', '.')
            }
        }
    }
}
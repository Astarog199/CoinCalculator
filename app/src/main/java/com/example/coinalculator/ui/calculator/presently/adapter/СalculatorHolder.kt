package com.example.coinalculator.ui.calculator.presently.adapter

import android.text.Editable
import android.text.TextWatcher
import androidx.recyclerview.widget.RecyclerView
import com.example.coinalculator.databinding.ListItemBinding
import com.example.coinalculator.ui.calculator.presently.states.CoinCalState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class СalculatorHolder(
    private val binding: ListItemBinding,
    private val textChange:(Float, CoinCalState) -> Unit,
    private val showError: (Exception) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
    private val scope = CoroutineScope(Dispatchers.IO)
    private var searchJob: Job? = null

        fun bind(item: CoinCalState?) {
            with(binding){
                text.text = item?.name
                editText.setText("")
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
                                if (arg.isNotEmpty()) {
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

                    override fun afterTextChanged(s: Editable?) {}
                })
            }
        }

    private fun getCost(item: CoinCalState?): String {
        return if (item?.name == "rub") {
            val res = (item.price * item.value)
            res.toString()
        } else {
            item?.getPriceValue().toString()
        }
    }
    }
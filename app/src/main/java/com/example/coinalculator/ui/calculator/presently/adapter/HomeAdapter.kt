package com.example.coinalculator.ui.calculator.presently.adapter

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.coinalculator.databinding.ListItemBinding
import com.example.coinalculator.ui.calculator.domain.CoinCalculator
import com.example.coinalculator.ui.calculator.presently.states.CoinCalState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class HomeAdapter(
    private val textChange:(Float, CoinCalState) -> Unit
) : RecyclerView.Adapter<HomeHolder>() {
    private val scope = CoroutineScope(Dispatchers.IO)
    private var values: List<CoinCalState> = emptyList()
    private var searchJob: Job? = null

    fun setData(data: List<CoinCalState>) {
        this.values = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeHolder {
        return HomeHolder(
            ListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = values.size

    override fun onBindViewHolder(holder: HomeHolder, position: Int) {
        val item = values.getOrNull(position)
        with(holder.binding){
            text.text = item?.name
            editText.setText("")
            fun addHint() : String{
                return if (item?.name == "rub") {
                    (item.price * item.value).toString()
                } else {
                    item?.getPriceValue().toString()
                }
            }
            editText.hint = addHint()

            editText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {}

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    searchJob?.cancel()
                    searchJob = scope.launch {


                        val arg =editText.text.toString()
                        if (arg.isNotEmpty()) {
                            item?.let {
                                textChange(arg.toFloat(), item)
                            }
                        }
                    }
                }

                override fun afterTextChanged(s: Editable?) {}
            })
        }
    }
}


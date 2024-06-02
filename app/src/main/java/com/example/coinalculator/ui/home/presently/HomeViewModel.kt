package com.example.coinalculator.ui.home.presently

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coinalculator.ui.home.data.CoinsListRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel private constructor(
    private val repository: CoinsListRepository
) : ViewModel() {

    constructor() : this(CoinsListRepository())

    private val _coins = MutableStateFlow<List<String>>(emptyList())
    val coins = _coins.asStateFlow()

    init {
        loadCoins()
    }

    private fun loadCoins() {
//        viewModelScope.launch(Dispatchers.IO){
//            kotlin.runCatching {
//                repository.getCoins()
//            }.fold(
//                onSuccess = {_coins.value = it},
//                onFailure = { Log.d("BlankViewModel", it.message ?: "") }
//            )
//        }
        viewModelScope.launch {
            try {
                _coins.value = repository.getCoins()
            } catch (e: Exception) {
                Log.d("BlankViewModel", e.message ?: "")
            }
        }
    }
}
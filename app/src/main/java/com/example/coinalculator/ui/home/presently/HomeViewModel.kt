package com.example.coinalculator.ui.home.presently

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coinalculator.ui.home.data.CoinsListRepository
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Observable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel (
    private val repository: CoinsListRepository
) : ViewModel() {

    private val _coins = MutableStateFlow<List<String>>(emptyList())
    val coins = _coins.asStateFlow()

    init {
        loadCoins()
    }

    private fun loadCoins() {
        viewModelScope.launch {
            try {
                repository.getCoins()
                    .subscribe({ data ->
                        _coins.value = data
                    }, { error ->
                        Log.d("BlankViewModel", error.message ?: "")
                    }
                    )
            } catch (e: Exception) {
                Log.d("BlankViewModel", e.message ?: "")
            }
        }
    }
}
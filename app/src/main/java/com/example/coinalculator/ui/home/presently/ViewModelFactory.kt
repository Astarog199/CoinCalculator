package com.example.coinalculator.ui.home.presently

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.coinalculator.ui.home.data.CoinsListRepository

class ViewModelFactory(
    private val repository: CoinsListRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        when{
            modelClass.isAssignableFrom(HomeViewModel::class.java) ->{
                @Suppress("UNCHEKED_CAST")
                return HomeViewModel(repository) as T
            }
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
package com.rolandoselvera.founditall.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rolandoselvera.founditall.data.local.db.ResultDao
import java.lang.IllegalArgumentException

class SearchViewModelFactory(private val resultDao: ResultDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SearchViewModel(resultDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
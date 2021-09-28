package com.rolandoselvera.founditall.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rolandoselvera.founditall.data.model.ResultModel
import com.rolandoselvera.founditall.domain.GetResultsUseCase
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {

    val resultModel = MutableLiveData<List<ResultModel>?>()
    val isLoading = MutableLiveData<Boolean>()

    var getResultsUseCase = GetResultsUseCase()

    fun onCreate() {
        viewModelScope.launch {
            isLoading.postValue(true)
            val result = getResultsUseCase()

            if (!result.isNullOrEmpty()) {
                resultModel.postValue(result)
                isLoading.postValue(false)
            } else {
                Log.e("TAG1", "Vacío")
            }
        }
    }
}
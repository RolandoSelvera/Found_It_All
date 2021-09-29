package com.rolandoselvera.founditall.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rolandoselvera.founditall.data.model.ResultModel
import com.rolandoselvera.founditall.data.remote.ResultsSearch
import com.rolandoselvera.founditall.domain.GetResultsUseCase
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {

    val resultModel = MutableLiveData<List<ResultModel>?>()
    val resultInfo = MutableLiveData<List<ResultsSearch>?>()
    val isLoadingModel = MutableLiveData<Boolean>()

    var getResultsUseCase = GetResultsUseCase()

    fun onCreate(search: String) {
        viewModelScope.launch {
            isLoadingModel.postValue(true)
            val result = getResultsUseCase.invokeResults(search)
            val info = getResultsUseCase.invokeInfo(search)

            if (!result.isNullOrEmpty() or !info.isNullOrEmpty()) {
                resultModel.postValue(result)
                resultInfo.postValue(info)
                isLoadingModel.postValue(false)
            } else {
                Log.e("TAG1", "Vacío")
            }
        }
    }

    fun selectedCategory(itemSelected: Int): Int {
        var item = itemSelected

        when (item) {
            0 -> item = 0
            1 -> item = 1
            2 -> item = 2
            3 -> item = 3
            4 -> item = 4
            5 -> item = 5
            6 -> item = 6
            7 -> item = 7
        }

        return item
    }
}
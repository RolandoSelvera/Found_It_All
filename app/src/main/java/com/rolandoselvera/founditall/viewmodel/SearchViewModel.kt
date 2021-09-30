package com.rolandoselvera.founditall.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.rolandoselvera.founditall.data.local.db.ResultDTO
import com.rolandoselvera.founditall.data.local.db.ResultDao
import com.rolandoselvera.founditall.data.model.ResultModel
import com.rolandoselvera.founditall.data.remote.ResultsSearch
import com.rolandoselvera.founditall.domain.GetResultsUseCase
import kotlinx.coroutines.launch

class SearchViewModel(private val resultDao: ResultDao) : ViewModel() {

    val allResultsDb: LiveData<List<ResultDTO>> = resultDao.getAllResults().asLiveData()

    val resultModel = MutableLiveData<List<ResultModel>?>()
    val resultInfo = MutableLiveData<List<ResultsSearch>?>()

    private var getResultsUseCase = GetResultsUseCase()

    fun invokeApiResults(search: String) {
        viewModelScope.launch {

            val result = getResultsUseCase.invokeResults(search)
            val info = getResultsUseCase.invokeInfo(search)

            if (!result.isNullOrEmpty() or !info.isNullOrEmpty()) {
                resultModel.postValue(result)
                resultInfo.postValue(info)

                resultDao.deleteAll()

                insertResults(resultInfo.value, resultModel.value)
            } else {
                Log.e("TAG1", "Vacío")
            }
        }
    }

    private fun insertResults(resultInfo: List<ResultsSearch>?, resultModel: List<ResultModel>?) {
        viewModelScope.launch {
            resultInfo?.map {
                ResultDTO(it.id, it.name, it.type, it.wikiTeaser, it.youTubeUrl, it.youTubeId)
            }?.let {
                resultDao.insert(it)
            }

            resultModel?.map {
                ResultDTO(it.id, it.name, it.type, it.wikiTeaser, it.youTubeUrl, it.youTubeId)
            }?.let { resultDao.insert(it) }
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
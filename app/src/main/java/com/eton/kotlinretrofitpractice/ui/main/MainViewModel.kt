package com.eton.kotlinretrofitpractice.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eton.kotlinretrofitpractice.api.ApiResult
import com.eton.kotlinretrofitpractice.repository.TestRepository
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val repository: TestRepository by lazy { TestRepository() }
    val mldUpdate = MutableLiveData<Boolean>()

    fun hardUpdate() =
        viewModelScope.launch {
            val response = repository.hardUpdate()
            println("response = $response")
            when (response) {
                is ApiResult.Success -> {
                    mldUpdate.value = true
                }

                is ApiResult.Error -> {
                    mldUpdate.value = false
                }
            }
        }
}
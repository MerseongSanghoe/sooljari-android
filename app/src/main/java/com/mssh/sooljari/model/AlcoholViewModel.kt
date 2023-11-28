package com.mssh.sooljari.model

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AlcoholViewModel(private val repository: AlcoholRepository) : ViewModel() {
    //검색 결과 리스트
    private val _alcoholList = MutableStateFlow<List<Alcohol>?>(emptyList())
    val alcoholList: StateFlow<List<Alcohol>?> = _alcoholList.asStateFlow()

    //술 상세 정보
    private val _alcoholInfo = MutableStateFlow<AlcoholInfo?>(null)
    val alcoholInfo: StateFlow<AlcoholInfo?> = _alcoholInfo

    //태그로 가져온 술 리스트
    private val _alcoholListByTag = MutableStateFlow<SearchedByTagAlcoholResults?>(null)
    val alcoholListByTag: StateFlow<SearchedByTagAlcoholResults?> = _alcoholListByTag

    //자동완성 키워드 리스트
    private val _keywordList = MutableStateFlow<List<String>?>(emptyList())
    val keywordList: StateFlow<List<String>?> = _keywordList

    //페이지네이션 변수
    private var currentPage = 0
    private var totalAlcoholCount = 0
    private var _isLoading = MutableStateFlow(false)
    private val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    fun initialLoad(keyword: String) {
        viewModelScope.launch {
            val result = repository.getInitialResults(keyword)
            currentPage = result.page ?: 0
            totalAlcoholCount = result.count ?: 0
            _alcoholList.value = result.data
            _isLoading.value = false
        }
    }

    fun loadMoreList(keyword: String) {
        if (isLoading.value) {
            Log.d("loadMoreList", "로딩중")
            return
        }

        _isLoading.value = true

        viewModelScope.launch {
            Log.d("loadMoreList", "로드 시작")
            if (canLoadMore()) {
                val nextPage = currentPage + 1
                val result = repository.getMoreResults(keyword, nextPage)

                currentPage = result.page ?: nextPage
                _alcoholList.value = _alcoholList.value?.plus(result.data)
                _isLoading.value = false
                Log.d("loadMoreList -> canLoadMore", "로드 함")
            }
        }
    }

    private fun canLoadMore(): Boolean {
        return _alcoholList.value?.size!! < totalAlcoholCount
    }

    fun login(id: String, pw: String) {
        viewModelScope.launch {
            repository.login(id, pw)
        }
    }

    fun getAlcoholInfo(id: Long) {
        viewModelScope.launch {
            _alcoholInfo.value = null
            _alcoholInfo.value = repository.getAlcoholInfo(id)
        }
    }

    fun getAlcoholsByTag(tag: String) {
        viewModelScope.launch {
            _alcoholListByTag.value = null
            _alcoholListByTag.value = repository.getAlcoholsByTag(tag)
        }
    }

    /*
    자동 완성 관련 함수들

    getAutocompleteKeywords

    updateUserInput
     */

    @OptIn(FlowPreview::class)
    fun getAutocompleteKeywords() {
        viewModelScope.launch {
            userInputFlow
                .debounce(300)
                .filter { it.isNotBlank() }
                .collect { input ->
                    val autoCompletedKeywords = repository.getAutoCompleteKeywords(input).data
                    _keywordList.value = autoCompletedKeywords
                }
        }
    }

    fun updateUserInput(input: String) {
        userInputFlow.value = input
    }
}

class AlcoholViewModelFactory(private val alcoholRepository: AlcoholRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(AlcoholViewModel::class.java)) {
            AlcoholViewModel(alcoholRepository) as T
        } else {
            throw IllegalArgumentException()
        }
    }
}
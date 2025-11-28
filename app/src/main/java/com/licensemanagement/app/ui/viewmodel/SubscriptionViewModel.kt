package com.licensemanagement.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.licensemanagement.app.data.model.CurrentSubscription
import com.licensemanagement.app.data.model.SubscriptionHistoryItem
import com.licensemanagement.app.data.model.SubscriptionHistoryResponse
import com.licensemanagement.app.data.repository.SubscriptionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SubscriptionViewModel : ViewModel() {
    private val subscriptionRepository = SubscriptionRepository()

    private val _currentSubscription = MutableStateFlow<CurrentSubscription?>(null)
    val currentSubscription: StateFlow<CurrentSubscription?> = _currentSubscription.asStateFlow()

    private val _history = MutableStateFlow<List<SubscriptionHistoryItem>>(emptyList())
    val history: StateFlow<List<SubscriptionHistoryItem>> = _history.asStateFlow()

    private val _uiState = MutableStateFlow<SubscriptionUiState>(SubscriptionUiState.Idle)
    val uiState: StateFlow<SubscriptionUiState> = _uiState.asStateFlow()

    private val _pagination = MutableStateFlow(PaginationState(1, 10, 0))
    val pagination: StateFlow<PaginationState> = _pagination.asStateFlow()

    fun loadCurrentSubscription(token: String) {
        viewModelScope.launch {
            _uiState.value = SubscriptionUiState.Loading
            subscriptionRepository.getCurrentSubscription(token)
                .getOrElse { error ->
                    _uiState.value = SubscriptionUiState.Error(error.message ?: "Failed to load subscription")
                    return@launch
                }.let { response ->
                    _currentSubscription.value = response.subscription
                    _uiState.value = SubscriptionUiState.Success
                }
        }
    }

    fun requestSubscription(token: String, sku: String) {
        viewModelScope.launch {
            _uiState.value = SubscriptionUiState.Loading
            subscriptionRepository.requestSubscription(token, sku)
                .getOrElse { error ->
                    _uiState.value = SubscriptionUiState.Error(error.message ?: "Failed to request subscription")
                    return@launch
                }.let {
                    _uiState.value = SubscriptionUiState.Success
                    loadCurrentSubscription(token)
                }
        }
    }

    fun deactivateSubscription(token: String) {
        viewModelScope.launch {
            _uiState.value = SubscriptionUiState.Loading
            subscriptionRepository.deactivateSubscription(token)
                .getOrElse { error ->
                    _uiState.value = SubscriptionUiState.Error(error.message ?: "Failed to deactivate subscription")
                    return@launch
                }.let {
                    _uiState.value = SubscriptionUiState.Success
                    loadCurrentSubscription(token)
                }
        }
    }

    fun loadHistory(token: String, page: Int = 1, limit: Int = 10, sort: String = "desc") {
        viewModelScope.launch {
            _uiState.value = SubscriptionUiState.Loading
            subscriptionRepository.getSubscriptionHistory(token, page, limit, sort)
                .getOrElse { error ->
                    _uiState.value = SubscriptionUiState.Error(error.message ?: "Failed to load history")
                    return@launch
                }.let { response ->
                    _history.value = response.history ?: emptyList()
                    response.pagination?.let { pag ->
                        _pagination.value = PaginationState(
                            pag.page,
                            pag.limit,
                            pag.total
                        )
                    }
                    _uiState.value = SubscriptionUiState.Success
                }
        }
    }

    // SDK Methods
    fun sdkLoadCurrentSubscription(apiKey: String) {
        viewModelScope.launch {
            _uiState.value = SubscriptionUiState.Loading
            subscriptionRepository.getSDKSubscription(apiKey)
                .getOrElse { error ->
                    _uiState.value = SubscriptionUiState.Error(error.message ?: "Failed to load subscription")
                    return@launch
                }.let { response ->
                    _currentSubscription.value = response.subscription
                    _uiState.value = SubscriptionUiState.Success
                }
        }
    }

    fun sdkRequestSubscription(apiKey: String, packSku: String) {
        viewModelScope.launch {
            _uiState.value = SubscriptionUiState.Loading
            subscriptionRepository.requestSDKSubscription(apiKey, packSku)
                .getOrElse { error ->
                    _uiState.value = SubscriptionUiState.Error(error.message ?: "Failed to request subscription")
                    return@launch
                }.let {
                    _uiState.value = SubscriptionUiState.Success
                    sdkLoadCurrentSubscription(apiKey)
                }
        }
    }

    fun sdkDeactivateSubscription(apiKey: String) {
        viewModelScope.launch {
            _uiState.value = SubscriptionUiState.Loading
            subscriptionRepository.deactivateSDKSubscription(apiKey)
                .getOrElse { error ->
                    _uiState.value = SubscriptionUiState.Error(error.message ?: "Failed to deactivate subscription")
                    return@launch
                }.let {
                    _uiState.value = SubscriptionUiState.Success
                    sdkLoadCurrentSubscription(apiKey)
                }
        }
    }

    fun sdkLoadHistory(apiKey: String, page: Int = 1, limit: Int = 10, sort: String = "desc") {
        viewModelScope.launch {
            _uiState.value = SubscriptionUiState.Loading
            subscriptionRepository.getSDKSubscriptionHistory(apiKey, page, limit, sort)
                .getOrElse { error ->
                    _uiState.value = SubscriptionUiState.Error(error.message ?: "Failed to load history")
                    return@launch
                }.let { response: SubscriptionHistoryResponse ->
                    _history.value = response.history ?: emptyList()
                    response.pagination?.let { pag ->
                        _pagination.value = PaginationState(
                            pag.page,
                            pag.limit,
                            pag.total
                        )
                    }
                    _uiState.value = SubscriptionUiState.Success
                }
        }
    }
}

sealed class SubscriptionUiState {
    object Idle : SubscriptionUiState()
    object Loading : SubscriptionUiState()
    object Success : SubscriptionUiState()
    data class Error(val message: String) : SubscriptionUiState()
}


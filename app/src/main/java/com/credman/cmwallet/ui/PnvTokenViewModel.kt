package com.credman.cmwallet.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.credman.cmwallet.data.repository.PnvTokenRepository
import com.credman.cmwallet.pnv.PnvTokenRegistry
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class PnvTokenUiState(
    val tokens: List<PnvTokenRegistry> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

class PnvTokenViewModel : ViewModel() {
    private val repository = PnvTokenRepository()
    
    private val _uiState = MutableStateFlow(PnvTokenUiState())
    val uiState: StateFlow<PnvTokenUiState> = _uiState.asStateFlow()

    private var hasInitialized = false

    init {
        viewModelScope.launch {
            repository.tokens.collect { tokens ->
                _uiState.update { currentState ->
                    currentState.copy(
                        tokens = tokens,
                        isLoading = false
                    )
                }
                
                // Auto-initialize with defaults if empty (only once)
                if (tokens.isEmpty() && !hasInitialized) {
                    hasInitialized = true
                    initializeDefaultTokens()
                }
            }
        }
    }

    fun initializeDefaultTokens() {
        viewModelScope.launch {
            try {
                _uiState.update { it.copy(isLoading = true) }
                repository.initializeDefaultTokens()
                _uiState.update { it.copy(isLoading = false, error = null) }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, error = e.message) }
            }
        }
    }

    fun saveToken(token: PnvTokenRegistry) {
        viewModelScope.launch {
            try {
                _uiState.update { it.copy(isLoading = true) }
                val existingToken = repository.getToken(token.tokenId)
                if (existingToken != null) {
                    repository.updateToken(token)
                } else {
                    repository.insertToken(token)
                }
                _uiState.update { it.copy(isLoading = false, error = null) }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, error = e.message) }
            }
        }
    }

    fun deleteToken(tokenId: String) {
        viewModelScope.launch {
            try {
                _uiState.update { it.copy(isLoading = true) }
                repository.deleteToken(tokenId)
                _uiState.update { it.copy(isLoading = false, error = null) }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, error = e.message) }
            }
        }
    }
}

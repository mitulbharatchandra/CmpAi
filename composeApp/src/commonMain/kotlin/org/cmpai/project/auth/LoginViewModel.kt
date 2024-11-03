package org.cmpai.project.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val authService: AuthService
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()

    private val _emailError = MutableStateFlow(false)
    val emailError = _emailError.asStateFlow()

    private val _passwordError = MutableStateFlow(false)
    val passwordError = _passwordError.asStateFlow()

    val currentUser: StateFlow<User?> = authService.currentUser.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        null
    )

    private val _isProcessing = MutableStateFlow(false)
    val isProcessing = _isProcessing.asStateFlow()

    private val isButtonEnabled: StateFlow<Boolean> = combine(uiState) { states ->
        val state = states.first()
        state.email.isNotBlank() && state.password.isNotBlank()
    }.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000), false
    )

    private val coroutineContext = SupervisorJob() + CoroutineExceptionHandler { _, throwable ->
        println("BaseViewModel: Error: ${throwable.message}")
        //show error message using snackbar for all errors
    }

    private var job: Job? =  null

    private fun launchWithCatchingException(block: suspend CoroutineScope.() -> Unit) {
        job = viewModelScope.launch(
            //context = coroutineContext,
            block = block
        )
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }

    fun onEmailChange(newValue: String) {
        _uiState.update { it.copy(email = newValue) }
        //reset error
        if (newValue.isNotBlank()) _emailError.value = false
    }

    fun onPasswordChange(newValue: String) {
        _uiState.update { it.copy(password = newValue) }
        //reset error
        if (newValue.isNotBlank()) _passwordError.value = false
    }

    fun onSignInClick() {

        if (_uiState.value.email.isEmpty()) {
            _emailError.value = true
            return
        }

        if (_uiState.value.password.isEmpty()) {
            _emailError.value = true
            return
        }

        launchWithCatchingException {
            _isProcessing.value = true
            //val result = authService.createUser(_uiState.value.email, _uiState.value.password)
            authService.authenticate(_uiState.value.email, _uiState.value.password)
            _isProcessing.value = false
        }

    }


    fun onSignOut() {
        launchWithCatchingException {
            authService.signOut()
        }
    }

}

data class LoginUiState(
    val email: String = "",
    val password: String = ""
)
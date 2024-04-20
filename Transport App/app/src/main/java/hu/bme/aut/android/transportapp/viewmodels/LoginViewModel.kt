package hu.bme.aut.android.transportapp.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.android.transportapp.data.userdata.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class LoginState {
    data object Loading: LoginState()

    data object LoadCompleted : LoginState()
    data object LoginStarted : LoginState()

    data class LoginFailed(val error: Throwable) : LoginState()

    data object LoginSucceeded : LoginState()
}

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: UserRepository
) : ViewModel(){

    private val _state = MutableStateFlow<LoginState>(LoginState.Loading)
    val state = _state.asStateFlow()
    var email by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    fun updateEmail(input: String){
        email = input
    }

    fun updatePassword(input: String){
        password = input
    }

    fun notifyLoadCompleted(){
        _state.value = LoginState.LoadCompleted
    }

    fun loginUser(onLoginClick: () -> Unit) {
        viewModelScope.launch {
            _state.value = LoginState.LoginStarted
            try{
                repository.authUserLogin(
                    email =  email,
                    password =  password
                )
            }catch (e: Exception){
                _state.value = LoginState.LoginFailed(e)
                e.printStackTrace()
            }
            _state.value = LoginState.LoginSucceeded
            onLoginClick()

        }
    }
}
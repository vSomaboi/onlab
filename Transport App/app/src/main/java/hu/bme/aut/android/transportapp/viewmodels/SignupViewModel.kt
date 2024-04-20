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

sealed class SignupState{
    data object Loading : SignupState()

    data object LoadCompleted : SignupState()
    data object SignupStarted : SignupState()

    data object CreatingUserAuthSucceeded : SignupState()

    data class Error(val error: Throwable): SignupState()

    data object AddingUserToDatabaseSucceeded : SignupState()
}

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val repository: UserRepository,
    //private val memDataStore: UserDataStore = UserDataStore()
) : ViewModel(){

    private val _state = MutableStateFlow<SignupState>(SignupState.Loading)
    val state = _state.asStateFlow()
    var email by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    var phoneNumber by mutableStateOf("")
        private set

    var confirmPassword by mutableStateOf("")
        private set

    fun updateEmail(input: String){
        email = input
    }

    fun updatePassword(input: String){
        password = input
    }

    fun updatePhoneNumber(input: String){
        phoneNumber = input
    }

    fun updateConfirmPassword(input: String){
        confirmPassword = input
    }

    fun notifyLoadCompleted(){
        _state.value = SignupState.LoadCompleted
    }

    fun registerUser(onSignupClick: () -> Unit){
        viewModelScope.launch {
            _state.value = SignupState.SignupStarted
            try{
                repository.createUserAuth(
                    email,
                    password
                )
            }catch (e: Exception){
                _state.value = SignupState.Error(e)
                e.printStackTrace()
            }
            _state.value = SignupState.CreatingUserAuthSucceeded
            try{
                repository.addUserToDatabase(
                    phoneNumber =  phoneNumber
                )
            }catch (e: Exception){
                _state.value = SignupState.Error(e)
                e.printStackTrace()
            }
            _state.value = SignupState.AddingUserToDatabaseSucceeded
            onSignupClick()
        }
    }
}
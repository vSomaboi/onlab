package hu.bme.aut.android.transportapp.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.android.transportapp.ui.model.RideUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

sealed class ProfileState{
    data object Loading: ProfileState()

    data class DisplayHistory(
        val announcedRides: List<RideUi>,
        val takenRides: List<RideUi>
    ) : ProfileState()
}

@HiltViewModel
class ProfileViewModel @Inject constructor(

) : ViewModel() {

    private val _state = MutableStateFlow<ProfileState>(ProfileState.Loading)
    val state = _state.asStateFlow()
    var phoneNumber by mutableStateOf("")
        private set

    fun updatePhoneNumber(input: String){
        phoneNumber = input
    }

    fun onModifyClicked(){

    }

    fun loadRides(){
        _state.value = ProfileState.DisplayHistory(
            listOf(
                RideUi(start = "Komlo", destination = "Pecs")
            ),
            listOf(
                RideUi(start = "Budapest", destination = "Komlo")
            )
        )
    }
}
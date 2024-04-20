package hu.bme.aut.android.transportapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.android.transportapp.data.applicationdata.ApplicationRepository
import hu.bme.aut.android.transportapp.data.ridedata.RideRepository
import hu.bme.aut.android.transportapp.domain.model.PendingApplication
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class PendingState{
    data object Loading: PendingState()
    data class ShowPendingApplications(val applications: List<PendingApplication>): PendingState()

    data class Error(val error: Throwable): PendingState()
}

@HiltViewModel
class PendingViewModel @Inject constructor(
    private val applicationRepository: ApplicationRepository,
    private val rideRepository: RideRepository
) : ViewModel(){
    private val _state = MutableStateFlow<PendingState>(PendingState.Loading)
    val state = _state.asStateFlow()

    fun loadApplications(){
        viewModelScope.launch {
            _state.value = PendingState.Loading
            try{
                val applications = applicationRepository.getPendingApplicationsOfCurrentUser()
                for (pendingApplication in applications) {
                    pendingApplication.destination = rideRepository.getDestinationOfRide(pendingApplication.rideID)
                }
                _state.value = PendingState.ShowPendingApplications(applications)
            }catch (e: Exception){
                _state.value = PendingState.Error(e)
            }
        }
    }

}
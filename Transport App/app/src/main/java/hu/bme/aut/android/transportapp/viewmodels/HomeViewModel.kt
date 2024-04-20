package hu.bme.aut.android.transportapp.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.android.transportapp.data.applicationdata.ApplicationRepository
import hu.bme.aut.android.transportapp.data.ridedata.RideRepository
import hu.bme.aut.android.transportapp.data.userdata.UserRepository
import hu.bme.aut.android.transportapp.domain.model.PendingApplication
import hu.bme.aut.android.transportapp.domain.model.Ride
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class HomeState{
    data object Loading : HomeState()

    data object LoadCompleted : HomeState()

    data object SearchStarted: HomeState()

    data class SearchResult(val rides : List<Ride>) : HomeState()

    data object PrepareDialogOpened: HomeState()

    data class Error(val error: Throwable) : HomeState()
}

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val rideRepository: RideRepository,
    private val applicationRepository: ApplicationRepository
) : ViewModel(){

    private val _state = MutableStateFlow<HomeState>(HomeState.Loading)
    val state = _state.asStateFlow()
    var rideStart by mutableStateOf("")
        private set

    fun updateRideStart(input: String){
        rideStart = input
    }

    fun notifyLoadCompleted(){
        _state.value = HomeState.LoadCompleted
    }

    fun onSearchClicked(){
        viewModelScope.launch{
            _state.value = HomeState.SearchStarted
            try{
                val rides = rideRepository.getRidesWithDestination(rideStart)
                _state.value = HomeState.SearchResult(rides)
            }catch (e: Exception){
                _state.value = HomeState.Error(e)
            }
        }

    }

    fun applyForRide(rideId: String){
        viewModelScope.launch {
            try{
                val destination = rideRepository.getDestinationOfRide(rideId)
                val userId = userRepository.getCurrentUser()?.uid!!
                val newApplication = PendingApplication(rideID = rideId, destination = destination, userID = userId)
                applicationRepository.createApplication(newApplication)
            }catch (e: Exception){
                _state.value = HomeState.Error(e)
            }
        }
    }

    fun revokeApplication(rideId: String){
        viewModelScope.launch{
            try{
                val userId = userRepository.getCurrentUser()?.uid!!
                val applicationID = applicationRepository.getApplicationWithUidAndRid(userID = userId, rideID = rideId).id
                applicationRepository.deleteApplication(applicationID)
            }catch (e: Exception){
                _state.value = HomeState.Error(e)
            }
        }
    }

    fun openPrepareDialog(){
        _state.value = HomeState.PrepareDialogOpened
    }
}
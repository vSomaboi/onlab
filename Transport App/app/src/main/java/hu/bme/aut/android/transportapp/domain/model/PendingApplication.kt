package hu.bme.aut.android.transportapp.domain.model

data class PendingApplication(
    val id: String = "",
    val userID: String = "",
    val rideID: String = "",
    val status: Boolean = false,
    var destination: String = ""
)

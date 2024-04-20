package hu.bme.aut.android.transportapp.domain.model

data class Ride(
    val id: String = "",
    val userId: String = "",
    val start: String = "",
    val destination: String = "",
    val metrics: String = "",
    val price: Int = 0
)
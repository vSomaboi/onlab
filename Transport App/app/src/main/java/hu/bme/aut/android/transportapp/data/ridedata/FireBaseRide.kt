package hu.bme.aut.android.transportapp.data.ridedata

import com.google.firebase.firestore.DocumentId
import hu.bme.aut.android.transportapp.domain.model.Ride

data class FireBaseRide(
    @DocumentId val id: String = "",
    val userId: String = "",
    val start: String = "",
    val destination: String = "",
    val metrics: String = "",
    val price: Int = 0
)

fun FireBaseRide.asRide() = Ride(
    id = id,
    userId = userId,
    start = start,
    destination = destination,
    metrics = metrics,
    price = price
)

fun Ride.asFireBaseRide() = FireBaseRide(
    id = id,
    userId = userId,
    start = start,
    destination = destination,
    metrics = metrics,
    price = price
)
package hu.bme.aut.android.transportapp.ui.model

import hu.bme.aut.android.transportapp.domain.model.Ride

data class RideUi(
    val id: String = "",
    val userId: String = "",
    val start: String = "",
    val destination: String = "",
    val length: Int = 0,
    val width: Int = 0,
    val height: Int = 0,
    val price: Int = 0
)

fun Ride.asRideUi() : RideUi {

    val separateMetrics = metrics.split("x")

    return RideUi(
        id = id,
        userId = userId,
        start = start,
        destination = destination,
        length = separateMetrics[0].toInt(),
        width = separateMetrics[1].toInt(),
        height = separateMetrics[2].toInt(),
        price = price
    )
}

fun RideUi.asRide() : Ride  = Ride(
    id = id,
    userId = userId,
    start = start,
    destination = destination,
    metrics = "${length}x${width}x${height}",
    price = price
)
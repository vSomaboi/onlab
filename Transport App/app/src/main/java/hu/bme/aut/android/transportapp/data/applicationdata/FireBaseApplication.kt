package hu.bme.aut.android.transportapp.data.applicationdata

import com.google.firebase.firestore.DocumentId
import hu.bme.aut.android.transportapp.domain.model.PendingApplication

data class FireBaseApplication (
    @DocumentId val id: String = "",
    val userID: String = "",
    val rideID: String = "",
    val status: Boolean = false
)

fun FireBaseApplication.asPendingApplication() = PendingApplication(
    id = id,
    userID = userID,
    rideID = rideID,
    status = status
)

fun PendingApplication.asFireBaseApplication() = FireBaseApplication(
    id = id,
    userID = userID,
    rideID = rideID,
    status = status
)
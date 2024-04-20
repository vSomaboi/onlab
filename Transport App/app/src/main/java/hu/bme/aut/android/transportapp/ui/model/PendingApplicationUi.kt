package hu.bme.aut.android.transportapp.ui.model

import hu.bme.aut.android.transportapp.domain.model.PendingApplication

data class PendingApplicationUi(
    val id: String = "",
    val destination: String = "",
    val status: Boolean = false
)

fun PendingApplication.asPendingApplicationUi() = PendingApplicationUi(
    id = id,
    status = status,
    destination = destination
)

fun PendingApplicationUi.asPendingApplication() = PendingApplication(
    id = id,
    status = status,
    destination = destination
)
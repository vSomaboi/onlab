package hu.bme.aut.android.transportapp.data.userdata

import com.google.firebase.firestore.DocumentId
import hu.bme.aut.android.transportapp.domain.model.User

data class FireBaseUser(
    @DocumentId val id: String = "",
    val phoneNumber: String = ""
)

fun FireBaseUser.asUser() = User(
    id = id,
    phoneNumber = phoneNumber
)

fun User.asFireBaseUser() = FireBaseUser(
    id = id,
    phoneNumber = phoneNumber
)
package hu.bme.aut.android.transportapp.data.userdata

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import hu.bme.aut.android.transportapp.domain.model.User
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
) {
    private var auth: FirebaseAuth = Firebase.auth
    private var db: FirebaseFirestore = Firebase.firestore


    fun getCurrentUser(): FirebaseUser? {
        return auth.currentUser
    }

    //Does NOT create a user record in the database, only creates
    //a new valid email-password pair
    suspend fun createUserAuth(
        email: String,
        password: String
    ) : AuthResult? {
        return auth.createUserWithEmailAndPassword(email, password).await()
    }

    //Creates new user record in the database
    suspend fun addUserToDatabase(user: User) {
        db.collection("users").add(user.asFireBaseUser()).await()
    }

    suspend fun authUserLogin(
        email: String,
        password: String
    ): AuthResult? {
        return auth.signInWithEmailAndPassword(email, password).await()

    }
    companion object{
        @Volatile private var instance: UserRepository? = null

        fun getInstance() =
            instance ?: synchronized(this){
                instance ?: UserRepository().also { instance = it }
            }
    }

}
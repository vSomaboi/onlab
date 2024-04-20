package hu.bme.aut.android.transportapp.data.applicationdata

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObjects
import com.google.firebase.ktx.Firebase
import hu.bme.aut.android.transportapp.domain.model.PendingApplication
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApplicationRepository @Inject constructor() {
    private var auth: FirebaseAuth = Firebase.auth
    private var db: FirebaseFirestore = Firebase.firestore

    suspend fun createApplication(application: PendingApplication) {
        db.collection("applications").add(application.asFireBaseApplication()).await()
    }

    suspend fun deleteApplication(
        id: String
    ){
        db.collection("applications").document(id).delete()
    }
    suspend fun getPendingApplicationsOfCurrentUser() : List<PendingApplication> {
        return db.collection("applications")
            .whereEqualTo("status", false)
            .whereEqualTo("userID", auth.currentUser?.uid)
            .get()
            .await()
            .toObjects<FireBaseApplication>()
            .map {
                it.asPendingApplication()
            }
    }

    suspend fun getApplicationsOfUser(
        userID: String
    ) : List<PendingApplication> {
        return db.collection("applications")
            .whereEqualTo("userID", userID)
            .get()
            .await()
            .toObjects<FireBaseApplication>()
            .map {
                it.asPendingApplication()
            }
    }

    suspend fun getApplicationWithUidAndRid(
        userID: String,
        rideID: String
    ) : PendingApplication {
        return db.collection("applications")
            .whereEqualTo("userID", userID)
            .whereEqualTo("rideID", rideID)
            .get()
            .await()
            .toObjects<FireBaseApplication>()
            .map {
                it.asPendingApplication()
            }.first()
    }

    companion object{
        @Volatile private var instance: ApplicationRepository? = null

        fun getInstance() =
            instance ?: synchronized(this){
                instance ?: ApplicationRepository().also { instance = it }
            }
    }
}
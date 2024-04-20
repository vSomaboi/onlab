package hu.bme.aut.android.transportapp.data.ridedata

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObjects
import com.google.firebase.ktx.Firebase
import hu.bme.aut.android.transportapp.domain.model.Ride
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RideRepository @Inject constructor() {
    private var db: FirebaseFirestore = Firebase.firestore

    suspend fun createRide(ride: Ride) {
        db.collection("rides").add(ride.asFireBaseRide()).await()
    }

    suspend fun deleteRide(
        id: String
    ){
        db.collection("rides").document(id).delete().await()
    }

    suspend fun getRidesWithDestination(
        destination: String
    ) : List<Ride> {
        return db.collection("rides")
            .whereEqualTo("destination", destination)
            .get()
            .await()
            .toObjects<FireBaseRide>()
            .map {
                it.asRide()
            }
    }

    suspend fun getAllRides(): List<Ride> {
        return db.collection("rides")
            .get()
            .await()
            .toObjects<FireBaseRide>()
            .map {
                it.asRide()
            }
    }


    suspend fun getRidesAnnouncedByUser(
        userID: Int
    ) : List<Ride> {
        return db.collection("rides")
            .whereEqualTo("userID", userID)
            .get()
            .await()
            .toObjects<FireBaseRide>()
            .map {
                it.asRide()
            }
    }

    suspend fun getRidesWithIds(
        rideIds: List<Int>
    ) : List<Ride> {
        return db.collection("rides")
            .whereIn("id", rideIds)
            .get()
            .await()
            .toObjects<FireBaseRide>()
            .map {
                it.asRide()
            }
    }

    suspend fun getDestinationOfRide(
        rideID: String
    ) : String {
        return db.collection("rides")
            .whereEqualTo("id", rideID)
            .get()
            .await()
            .toObjects<FireBaseRide>()
            .map {
                it.asRide()
            }.first()
            .destination
    }

    companion object{
        @Volatile private var instance: RideRepository? = null

        fun getInstance() =
            instance ?: synchronized(this){
                instance ?: RideRepository().also { instance = it }
            }
    }
}
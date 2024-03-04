package hu.bme.aut.android.transportapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            //MyApp()
        }
    }
}

/*// Define the Profile composable.
@Composable
fun Profile(onNavigateToFriendsList: () -> Unit, messageText: String) {
    Column {
        Text(messageText)
        Button(onClick = { onNavigateToFriendsList() }) {
            Text("Go to Friends List")
        }
    }
}

// Define the FriendsList composable.
@Composable
fun FriendsList(onNavigateToProfile: () -> Unit) {
    Column {
        Text("Friends List")
        Button(onClick = { onNavigateToProfile() }) {
            Text("Go to Profile")
        }
    }
}

// Define the MyApp composable, including the `NavController` and `NavHost`.
@Composable
fun MyApp() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "profile/{message}") {
        composable("profile/{message}",
                    arguments = listOf(navArgument("message"){ type = NavType.StringType})
        ) { backStackEntry ->
            Profile(onNavigateToFriendsList =
            { navController.navigate("friendslist")},
                backStackEntry.arguments?.getString("message") ?: "null volt")
        }
        composable("friendslist") { FriendsList(onNavigateToProfile = { navController.navigate("profile/vajon mukodik?") }) }
    }
}*/


package hu.bme.aut.android.transportapp.compose

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import hu.bme.aut.android.transportapp.compose.home.HomeScreen
import hu.bme.aut.android.transportapp.compose.login.LoginScreen
import hu.bme.aut.android.transportapp.compose.manageapplicants.ManageApplicantsScreen
import hu.bme.aut.android.transportapp.compose.pendingapplications.PendingApplicationsScreen
import hu.bme.aut.android.transportapp.compose.prepareride.PrepareRideScreen
import hu.bme.aut.android.transportapp.compose.profile.ProfileScreen
import hu.bme.aut.android.transportapp.compose.signup.SignUpScreen

@Composable
fun TransportApp(){
    val navController = rememberNavController()
    TransportNavHost(
        navController = navController
    )
}

@Composable
fun TransportNavHost(
    navController: NavHostController
){
    //val activity = (LocalContext.current as Activity)
    NavHost(navController = navController, startDestination = Screen.Login.route){
        composable(route = Screen.Login.route){
            LoginScreen(
                onLoginClick = {
                    navController.navigate(
                        Screen.Home.route
                    )
                },
                onSignupClick = {
                    navController.navigate(
                        Screen.SignUp.route
                    )
                }
            )
        }
        composable(route = Screen.SignUp.route){
            SignUpScreen(
                onBackClick = {
                    navController.navigate(
                        Screen.Login.route
                    )
                },
                onSignupClick = {
                    navController.navigate(
                        Screen.Home.route
                    )
                }
            )
        }

        composable(
            route = Screen.Home.route,
        ) {
            HomeScreen(
                onProfileClick = {
                    navController.navigate(
                        Screen.Profile.route
                    )
                },
                onPendingClick = {
                    navController.navigate(
                        Screen.PendingApplications.route
                    )
                },
                onViewApplicantsClick = {
                    navController.navigate(
                        Screen.ManageApplicants.route
                    )
                }
            )
        }

        composable(
            route = Screen.Profile.route
        ){
            ProfileScreen(
                onBackClick = {
                    navController.navigate(
                        Screen.Home.route
                    )
                }
            )
        }

        composable(
            route = Screen.PendingApplications.route
        ){
            PendingApplicationsScreen(
                onBackClick = {
                    navController.navigate(
                        Screen.Home.route
                    )
                }
            )
        }

        composable(
            route = Screen.PrepareRide.route
        ){
            PrepareRideScreen()
        }

        composable(
            route = Screen.ManageApplicants.route
        ){
            ManageApplicantsScreen()
        }

    }

}
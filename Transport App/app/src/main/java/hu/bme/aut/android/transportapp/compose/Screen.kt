package hu.bme.aut.android.transportapp.compose

import androidx.navigation.NamedNavArgument

sealed class Screen(
    val route: String,
    val navArguments: List<NamedNavArgument> = emptyList()
){
    data object Login : Screen("login")
    data object SignUp : Screen("signup")

    data object Home : Screen("home")
    data object Profile : Screen("profile")

    data object PendingApplications : Screen("pending_applications")

    data object PrepareRide: Screen("prepare_ride")

    data object ManageApplicants: Screen("manage_applicants")
}
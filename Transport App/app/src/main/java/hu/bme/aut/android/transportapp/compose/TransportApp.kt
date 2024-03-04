package hu.bme.aut.android.transportapp.compose

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

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

}
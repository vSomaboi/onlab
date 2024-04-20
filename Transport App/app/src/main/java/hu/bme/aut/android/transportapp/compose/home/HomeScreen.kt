package hu.bme.aut.android.transportapp.compose.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import hu.bme.aut.android.composenavigation.R
import hu.bme.aut.android.transportapp.data.ridedata.RideRepository
import hu.bme.aut.android.transportapp.ui.common.RowWithTextFieldAndButton
import hu.bme.aut.android.transportapp.ui.common.TopAppBarWithProfileIcon
import hu.bme.aut.android.transportapp.ui.common.TransportAppLoadingScreen
import hu.bme.aut.android.transportapp.ui.model.asRideUi
import hu.bme.aut.android.transportapp.viewmodels.HomeState
import hu.bme.aut.android.transportapp.viewmodels.HomeViewModel

@Composable
fun HomeScreen(
    viewModel : HomeViewModel = hiltViewModel(),
    onProfileClick: () -> Unit = {},
    onPendingClick: () -> Unit = {},
    onViewApplicantsClick: () -> Unit = {}
){
    val state = viewModel.state.collectAsState().value

    LaunchedEffect(key1 = null) {
        viewModel.notifyLoadCompleted()
    }


    val marginBetweenElements = dimensionResource(id = R.dimen.margin_between_text_fields)
    val marginSides = dimensionResource(id = R.dimen.button_margin_small)

    when(state){
        is HomeState.Loading -> {
            TransportAppLoadingScreen(
                text = stringResource(id = R.string.loading_screen_message)
            )
        }
        is HomeState.LoadCompleted -> {
            Scaffold(
                topBar = {
                    TopAppBarWithProfileIcon(
                        text = stringResource(id = R.string.homeTitle),
                        onProfileClick = onProfileClick
                    )
                },
                content = {paddingValues ->
                    ConstraintLayout(
                        modifier = Modifier
                            .padding(paddingValues)
                            .fillMaxSize()
                            .background(
                                brush = Brush.linearGradient(
                                    colorStops = arrayOf(
                                        0.0f to Color.White,
                                        0.7f to Color.Cyan,
                                        1f to Color.Blue
                                    )
                                )
                            )
                    ) {
                        val maxLength = 20
                        val inputRow = createRef()

                        RowWithTextFieldAndButton(
                            modifier = Modifier.constrainAs(inputRow) {
                                top.linkTo(
                                    anchor = parent.top,
                                    margin = 5.dp
                                )
                                start.linkTo(
                                    anchor = parent.start,
                                    margin = 5.dp
                                )
                                end.linkTo(
                                    anchor = parent.end,
                                    margin = 5.dp
                                )
                                width = Dimension.fillToConstraints
                            },
                            value = viewModel.rideStart,
                            onValueChange = {rideStart ->
                                if(rideStart.length <= maxLength){
                                    viewModel.updateRideStart(rideStart)
                                }
                            },
                            onClick = { viewModel.onSearchClicked() },
                            buttonIcon = painterResource(id = R.drawable.ic_search_rides),
                            buttonDescription = stringResource(id = R.string.searchRidesDescription)
                        )
                    }

                }
            )
        }
        is HomeState.SearchStarted -> {

        }
        is HomeState.SearchResult -> {
            Scaffold(
                topBar = {
                    TopAppBarWithProfileIcon(
                        text = stringResource(id = R.string.homeTitle),
                        onProfileClick = onProfileClick
                    )
                },
                content = {paddingValues ->
                    ConstraintLayout(
                        modifier = Modifier
                            .padding(paddingValues)
                            .fillMaxSize()
                            .background(
                                brush = Brush.linearGradient(
                                    colorStops = arrayOf(
                                        0.0f to Color.White,
                                        0.7f to Color.Cyan,
                                        1f to Color.Blue
                                    )
                                )
                            )
                    ) {
                        val maxLength = 20
                        val (inputRow, listView, btnViewPending, btnPrepareRide, btnViewApplicants) = createRefs()

                        RowWithTextFieldAndButton(
                            modifier = Modifier.constrainAs(inputRow) {
                                top.linkTo(
                                    anchor = parent.top,
                                    margin = 5.dp
                                )
                                start.linkTo(
                                    anchor = parent.start,
                                    margin = 5.dp
                                )
                                end.linkTo(
                                    anchor = parent.end,
                                    margin = 5.dp
                                )
                                width = Dimension.fillToConstraints
                            },
                            value = viewModel.rideStart,
                            onValueChange = {rideStart ->
                                if(rideStart.length <= maxLength){
                                    viewModel.updateRideStart(rideStart)
                                }
                            },
                            onClick = { viewModel.onSearchClicked() },
                            buttonIcon = painterResource(id = R.drawable.ic_search_rides),
                            buttonDescription = stringResource(id = R.string.searchRidesDescription)
                        )

                        Spacer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(20.dp)
                        )

                        LazyColumn(
                            modifier = Modifier
                                .constrainAs(listView) {
                                    top.linkTo(
                                        anchor = inputRow.bottom,
                                        margin = marginBetweenElements
                                    )
                                    start.linkTo(
                                        anchor = parent.start,
                                        margin = marginSides
                                    )
                                    end.linkTo(
                                        anchor = parent.end,
                                        margin = marginSides
                                    )
                                    width = Dimension.fillToConstraints
                                }
                        ) {
                            items(state.rides.map { it.asRideUi() }, key = {ride -> ride.id}){ride ->
                                ListItem(
                                    headlineContent = {
                                        Column {
                                            Row(
                                                modifier = Modifier
                                                    .fillMaxWidth(),
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.SpaceBetween
                                            ){
                                                Text(
                                                    text = ride.start,
                                                    fontSize = 20.sp,
                                                    fontWeight = FontWeight(1000)
                                                )
                                                Icon(
                                                    painter = painterResource(id = R.drawable.ic_arrow_forward),
                                                    contentDescription = null
                                                )
                                                Text(
                                                    text = ride.destination,
                                                    fontSize = 20.sp,
                                                    fontWeight = FontWeight(1000)
                                                )
                                            }
                                            Row(
                                                modifier = Modifier
                                                    .fillMaxWidth(),
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.SpaceBetween
                                            ){
                                                Text(
                                                    text = stringResource(id = R.string.listItemHeadLineInfo)
                                                )
                                                Text(text = "${ride.length} x ${ride.width} x ${ride.height} (cm)")
                                            }
                                        }
                                    },
                                    supportingContent = {
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth(),
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.SpaceAround
                                        ){
                                            Button(
                                                onClick = { viewModel.applyForRide(rideId = ride.id) },
                                                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
                                            ){
                                                Text(
                                                    text = stringResource(id = R.string.applyButtonText),
                                                    color = Color.Green
                                                )
                                            }
                                            Button(
                                                onClick = { viewModel.revokeApplication(rideId = ride.id) },
                                                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
                                            ){
                                                Text(
                                                    text = stringResource(id = R.string.revokeButtonText),
                                                    color = Color.Red
                                                )
                                            }
                                        }
                                    }
                                )
                            }
                        }
                        Spacer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(20.dp)
                        )

                        Button(
                            onClick = { viewModel.loadApplicationList(onPendingClick) },
                            modifier = Modifier
                                .constrainAs(btnViewPending){
                                    bottom.linkTo(
                                        anchor = btnPrepareRide.top,
                                        margin = marginSides
                                    )
                                    start.linkTo(
                                        anchor = parent.start,
                                        margin = marginSides
                                    )
                                }
                        ){
                            Text(
                                text = stringResource(id = R.string.btnPendingText),
                                fontSize = 20.sp
                            )
                        }
                        Button(
                            onClick = { viewModel.openPrepareDialog() },
                            modifier = Modifier
                                .constrainAs(btnPrepareRide){
                                    bottom.linkTo(
                                        anchor = btnViewApplicants.top,
                                        margin = marginSides
                                    )
                                    end.linkTo(
                                        anchor = parent.end,
                                        margin = marginSides
                                    )
                                }
                        ){
                            Text(
                                text = stringResource(id = R.string.btnPrepareRideText),
                                fontSize = 20.sp
                            )
                        }
                        Button(
                            onClick = { viewModel.loadApplicants(onViewApplicantsClick) },
                            modifier = Modifier
                                .constrainAs(btnViewApplicants){
                                    bottom.linkTo(
                                        anchor = parent.bottom,
                                        margin = marginSides
                                    )
                                    start.linkTo(
                                        anchor = parent.start,
                                        margin = marginSides
                                    )
                                }
                        ){
                            Text(
                                text = stringResource(id = R.string.btnViewApplicantsText),
                                fontSize = 20.sp
                            )
                        }
                    }

                }
            )
        }
        is HomeState.Error -> {

        }

        is HomeState.PrepareDialogOpened -> {

        }
    }



}


@Preview
@Composable
fun HomeScreenPreView(){
    val rideRepository = RideRepository.getInstance()
    val viewModel = HomeViewModel(rideRepository)
    HomeScreen(viewModel = viewModel)
}
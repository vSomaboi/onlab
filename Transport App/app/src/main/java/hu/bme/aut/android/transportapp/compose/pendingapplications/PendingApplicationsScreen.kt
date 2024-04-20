package hu.bme.aut.android.transportapp.compose.pendingapplications

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import hu.bme.aut.android.composenavigation.R
import hu.bme.aut.android.transportapp.data.applicationdata.ApplicationRepository
import hu.bme.aut.android.transportapp.data.ridedata.RideRepository
import hu.bme.aut.android.transportapp.ui.common.TransportAppLoadingScreen
import hu.bme.aut.android.transportapp.ui.model.asPendingApplicationUi
import hu.bme.aut.android.transportapp.ui.theme.TopAppBarBackgroundColor
import hu.bme.aut.android.transportapp.viewmodels.PendingState
import hu.bme.aut.android.transportapp.viewmodels.PendingViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PendingApplicationsScreen(
    viewModel: PendingViewModel = hiltViewModel(),
    onBackClick: () -> Unit = {},
){
    val state = viewModel.state.collectAsState().value

    LaunchedEffect(key1 = null) {
        viewModel.loadApplications()
    }

    val marginBetweenElements = dimensionResource(id = R.dimen.margin_between_text_fields)
    val marginSides = dimensionResource(id = R.dimen.button_margin_small)

    when(state){
        is PendingState.Loading -> {
            TransportAppLoadingScreen(text = stringResource(id = R.string.loading_screen_message))
        }
        is PendingState.ShowPendingApplications -> {
            Scaffold(
                topBar = {
                    TopAppBar(
                        modifier = Modifier
                            .background(
                                brush = SolidColor(
                                    value = TopAppBarBackgroundColor
                                )
                            ),
                        title = {Text(text = stringResource(id = R.string.pendingTitle))},
                        navigationIcon = {
                            IconButton(
                                onClick = onBackClick,
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_arrow_back),
                                    contentDescription = null
                                )
                            }
                        },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = Color.Transparent
                        )
                    )
                },
                content = { paddingValues ->
                    ConstraintLayout(
                        modifier = Modifier
                            .padding(paddingValues)
                    ) {
                        val (headerRow,lazyCol) = createRefs()

                        Row(
                            modifier = Modifier
                            .fillMaxWidth()
                            .constrainAs(headerRow){
                                  top.linkTo(
                                      anchor = parent.top,
                                      margin = marginBetweenElements
                                  )
                                  start.linkTo(
                                      anchor = parent.start
                                  )
                            },
                            horizontalArrangement = Arrangement.SpaceAround,
                            verticalAlignment = Alignment.CenterVertically
                        ){
                            Text(
                                text = stringResource(id = R.string.pendingScreenDestColHeader),
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = stringResource(id = R.string.pendingScreenStatusColHeader),
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        LazyColumn(
                            modifier = Modifier
                                .constrainAs(lazyCol){
                                    top.linkTo(
                                        anchor = headerRow.bottom,
                                        margin = marginBetweenElements
                                    )
                                    start.linkTo(
                                        anchor = parent.start
                                    )
                                    width = Dimension.fillToConstraints
                                }
                        ) {
                            items(state.applications.map { it.asPendingApplicationUi() }, key = {application -> application.id}){ application ->
                                ListItem(
                                    headlineContent = {
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
                                        ){
                                            Text(
                                                text = application.destination,
                                                fontSize = 20.sp
                                            )
                                            if(application.status){
                                                Text(
                                                    text = stringResource(id = R.string.applicationAccepted),
                                                    textAlign = TextAlign.Center,
                                                    color = Color.Green
                                                )
                                            }
                                            else{
                                                Text(
                                                    text = stringResource(id = R.string.applicationDenied),
                                                    textAlign = TextAlign.Center,
                                                    color = Color.Red
                                                )
                                            }
                                        }
                                    }
                                )

                            }
                        }
                    }
                }
            )
        }
        is PendingState.Error -> {

        }
    }

}

@Composable
@Preview
fun PendingApplicationsScreenPreview(){
    val applicationRepository = ApplicationRepository.getInstance()
    val rideRepository = RideRepository.getInstance()
    val viewModel = PendingViewModel(applicationRepository, rideRepository)
    PendingApplicationsScreen(viewModel = viewModel)
}
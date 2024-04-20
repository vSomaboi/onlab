package hu.bme.aut.android.transportapp.compose.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import hu.bme.aut.android.composenavigation.R
import hu.bme.aut.android.transportapp.ui.common.BasicTopBar
import hu.bme.aut.android.transportapp.ui.common.TransportAppLoadingScreen
import hu.bme.aut.android.transportapp.viewmodels.ProfileState
import hu.bme.aut.android.transportapp.viewmodels.ProfileViewModel


@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel = hiltViewModel(),
    onBackClick: () -> Unit = {}
){
    val state = viewModel.state.collectAsState().value

    LaunchedEffect(key1 = null) {
        viewModel.loadRides()
    }

    when(state){
        is ProfileState.Loading -> {
            TransportAppLoadingScreen(text = stringResource(id = R.string.loading_screen_message))
        }
        is ProfileState.DisplayHistory -> {
            Scaffold(
                topBar = {
                    BasicTopBar(
                        text = stringResource(id = R.string.profileTitle),
                        onBackClick = onBackClick
                    )
                },
                content = { paddingValues ->
                    ConstraintLayout(
                        modifier = Modifier
                            .padding(paddingValues)
                            .fillMaxSize()
                    ) {
                        val (tPhone, etPhone, btnModify, tHistory,
                            tAnnounced, tTaken, lcAnnounced, lcTaken) = createRefs()

                        val marginLarge = dimensionResource(id = R.dimen.margin_between_text_fields)
                        val marginSmall = dimensionResource(id = R.dimen.button_margin_small)

                        val glStart = createGuidelineFromStart(0.15f)
                        val glEnd = createGuidelineFromEnd(0.15f)
                        val glBoxDivider = createGuidelineFromTop(0.6f)


                        Text(
                            modifier = Modifier
                                .constrainAs(tPhone){
                                    start.linkTo(
                                        anchor = parent.start,
                                        margin = marginSmall
                                    )
                                    top.linkTo(
                                        anchor = parent.top,
                                        margin = marginLarge
                                    )
                                },
                            text = stringResource(id = R.string.profilePhoneNumber),
                            fontSize = 25.sp
                        )

                        OutlinedTextField(
                            modifier = Modifier
                                .constrainAs(etPhone) {
                                    start.linkTo(
                                        anchor = parent.start,
                                        margin = marginSmall
                                    )
                                    top.linkTo(
                                        anchor = tPhone.bottom,
                                        margin = marginSmall
                                    )
                                    end.linkTo(
                                        anchor = btnModify.start,
                                        margin = marginSmall
                                    )
                                    width = Dimension.fillToConstraints
                                },
                            value = viewModel.phoneNumber,
                            onValueChange = { phone -> viewModel.updatePhoneNumber(phone) }
                        )

                        Button(
                            modifier = Modifier
                                .constrainAs(btnModify) {
                                    end.linkTo(
                                        anchor = parent.end,
                                        margin = marginSmall
                                    )
                                    top.linkTo(etPhone.top)
                                    bottom.linkTo(etPhone.bottom)
                                }
                                .height(ButtonDefaults.MinHeight),
                            onClick = { viewModel.onModifyClicked() }
                        ) {
                            Text(
                                text = stringResource(id = R.string.modifyPhoneButton),
                                fontSize = 20.sp
                            )
                        }

                        Text(
                            modifier = Modifier
                                .constrainAs(tHistory){
                                    start.linkTo(
                                        anchor = parent.start,
                                        margin = marginSmall
                                    )
                                    top.linkTo(
                                        anchor = etPhone.bottom,
                                        margin = marginLarge
                                    )
                                },
                            text = stringResource(id = R.string.profileHistoryText),
                            fontSize = 25.sp
                        )

                        Text(
                            modifier = Modifier
                                .constrainAs(tAnnounced){
                                    start.linkTo(glStart)
                                    top.linkTo(
                                        anchor = tHistory.bottom,
                                        margin = marginLarge
                                    )
                                },
                            text = stringResource(id = R.string.profileHistorySubtextAnnounced),
                            fontSize = 20.sp
                        )
                        LazyColumn(
                            modifier = Modifier
                                .constrainAs(lcAnnounced) {
                                    start.linkTo(glStart)
                                    top.linkTo(
                                        anchor = tAnnounced.bottom,
                                        margin = marginSmall
                                    )
                                    bottom.linkTo(glBoxDivider)
                                    end.linkTo(glEnd)
                                    height = Dimension.fillToConstraints
                                    width = Dimension.fillToConstraints
                                }
                                .background(
                                    brush = SolidColor(Color.LightGray)
                                )
                        ) {
                            items(state.announcedRides, key = {ride -> ride.id}){ride ->
                                ListItem(
                                    headlineContent = {
                                        Row(
                                            modifier = Modifier
                                            .fillMaxWidth(),
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ){
                                            Text(
                                                text = ride.start,
                                                fontWeight = FontWeight.Bold
                                            )
                                            Icon(
                                                painter = painterResource(id = R.drawable.ic_arrow_forward),
                                                contentDescription = null
                                            )
                                            Text(
                                                text = ride.destination,
                                                fontWeight = FontWeight.Bold
                                            )
                                        }
                                    },
                                    supportingContent = {
                                        HorizontalDivider(
                                            color = Color.Black
                                        )
                                    }
                                )
                            }
                        }
                        Text(
                            modifier = Modifier
                                .constrainAs(tTaken){
                                    start.linkTo(glStart)
                                    top.linkTo(
                                        anchor = glBoxDivider,
                                        margin = marginLarge
                                    )
                                },
                            text = stringResource(id = R.string.profileHistorySubtextTaken),
                            fontSize = 20.sp
                        )

                        LazyColumn(
                            modifier = Modifier
                                .constrainAs(lcTaken) {
                                    start.linkTo(glStart)
                                    top.linkTo(
                                        anchor = tTaken.bottom,
                                        margin = marginSmall
                                    )
                                    bottom.linkTo(
                                        anchor = parent.bottom,
                                        margin = marginLarge
                                    )
                                    end.linkTo(glEnd)
                                    height = Dimension.fillToConstraints
                                    width = Dimension.fillToConstraints
                                }
                                .background(
                                    brush = SolidColor(Color.LightGray)
                                )
                        ) {
                            items(state.takenRides, key = {ride -> ride.id}){ ride ->
                                ListItem(
                                    headlineContent = {
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth(),
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ){
                                            Text(
                                                text = ride.start,
                                                fontWeight = FontWeight.Bold
                                            )
                                            Icon(
                                                painter = painterResource(id = R.drawable.ic_arrow_forward),
                                                contentDescription = null
                                            )
                                            Text(
                                                text = ride.destination,
                                                fontWeight = FontWeight.Bold
                                            )
                                        }
                                    },
                                    supportingContent = {
                                        HorizontalDivider(
                                            color = Color.Black
                                        )
                                    }
                                )
                            }
                        }
                    }
                }
            )
        }
    }
}

@Composable
@Preview
fun ProfileScreenPreview(){
    ProfileScreen()
}
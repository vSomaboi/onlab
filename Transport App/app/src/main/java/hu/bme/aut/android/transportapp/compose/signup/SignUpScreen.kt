package hu.bme.aut.android.transportapp.compose.signup

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import hu.bme.aut.android.composenavigation.R
import hu.bme.aut.android.transportapp.data.userdata.UserRepository
import hu.bme.aut.android.transportapp.ui.common.BasicTopBar
import hu.bme.aut.android.transportapp.ui.common.TransportAppLoadingScreen
import hu.bme.aut.android.transportapp.viewmodels.SignupState
import hu.bme.aut.android.transportapp.viewmodels.SignupViewModel

@Composable
fun SignUpScreen(
    viewModel: SignupViewModel = hiltViewModel(),
    onSignupClick: () -> Unit = {},
    onBackClick: () -> Unit = {}
){
    val state = viewModel.state.collectAsState().value

    LaunchedEffect(key1 = null) {
        viewModel.notifyLoadCompleted()
    }

    when(state){
        is SignupState.Loading -> {
            TransportAppLoadingScreen(
                text = stringResource(id = R.string.loading_screen_message)
            )
        }
        is SignupState.LoadCompleted -> {
            Scaffold(
                topBar = {
                    BasicTopBar(
                        text = stringResource(id = R.string.signupTitle),
                        onBackClick = onBackClick
                    )
                },
                content = { paddingValues ->
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
                        val (etEmail, etPassword, etConfirmPassword, etPhoneNumber, btnSignup) = createRefs()

                        val glStart = createGuidelineFromStart(0.2f)
                        val glEnd = createGuidelineFromEnd(0.2f)
                        val glVerticalCenter = createGuidelineFromTop(0.5f)

                        val buttonMargin = dimensionResource(id = R.dimen.button_margin_large)
                        val textFieldMargin = dimensionResource(id = R.dimen.margin_between_text_fields)

                        OutlinedTextField(
                            modifier = Modifier
                                .constrainAs(etEmail) {
                                    start.linkTo(glStart)
                                    end.linkTo(glEnd)
                                    bottom.linkTo(
                                        anchor = etPassword.top,
                                        margin = textFieldMargin
                                    )
                                },
                            value = viewModel.email,
                            onValueChange = { email -> viewModel.updateEmail(email) },
                            label = { Text(text = stringResource(id = R.string.enter_email_address)) }
                        )
                        OutlinedTextField(
                            modifier = Modifier
                                .constrainAs(etPassword) {
                                    start.linkTo(glStart)
                                    end.linkTo(glEnd)
                                    bottom.linkTo(
                                        anchor = etConfirmPassword.top,
                                        margin = textFieldMargin
                                    )
                                },
                            value = viewModel.password,
                            onValueChange = { password -> viewModel.updatePassword(password) },
                            label = { Text(text = stringResource(id = R.string.enter_password)) },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                        )
                        OutlinedTextField(
                            modifier = Modifier
                                .constrainAs(etConfirmPassword) {
                                    start.linkTo(glStart)
                                    end.linkTo(glEnd)
                                    top.linkTo(glVerticalCenter)
                                    bottom.linkTo(glVerticalCenter)
                                },
                            value = viewModel.confirmPassword,
                            onValueChange = { confirmPassword ->
                                viewModel.updateConfirmPassword(
                                    confirmPassword
                                )
                            },
                            label = { Text(text = stringResource(id = R.string.confirm_password)) },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                        )
                        OutlinedTextField(
                            modifier = Modifier
                                .constrainAs(etPhoneNumber){
                                    start.linkTo(glStart)
                                    end.linkTo(glEnd)
                                    top.linkTo(
                                        anchor = etConfirmPassword.bottom,
                                        margin = textFieldMargin
                                    )
                                },
                            value = viewModel.phoneNumber,
                            onValueChange = { phoneNumber ->
                                viewModel.updatePhoneNumber(
                                    phoneNumber
                                )
                            },
                            label = {Text(text = stringResource(id = R.string.enter_phone_number))}
                        )
                        Button(
                            modifier = Modifier
                                .border(
                                    width = 2.dp,
                                    brush = Brush.horizontalGradient(
                                        colorStops = arrayOf(
                                            0.0f to Color.DarkGray,
                                            1f to Color.Black
                                        )
                                    ),
                                    shape = ButtonDefaults.shape
                                )
                                .background(
                                    brush = Brush.horizontalGradient(
                                        colors = listOf(
                                            Color.White,
                                            Color.Cyan
                                        )
                                    ), shape = ButtonDefaults.shape
                                )
                                .height(ButtonDefaults.MinHeight)
                                .constrainAs(btnSignup) {
                                    start.linkTo(glStart)
                                    end.linkTo(glEnd)
                                    top.linkTo(
                                        anchor = etPhoneNumber.bottom,
                                        margin = buttonMargin
                                    )
                                    width = Dimension.fillToConstraints
                                },
                            onClick = {
                                viewModel.registerUser(onSignupClick)
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
                        ) {
                            Text(
                                text = stringResource(id = R.string.signup),
                                fontSize = 20.sp,
                                color = Color.Black
                            )
                        }
                    }
                }
            )
        }
        is SignupState.SignupStarted -> {
            TransportAppLoadingScreen(
                text = stringResource(id = R.string.creating_user_auth)
            )
        }
        is SignupState.Error -> {

        }
        is SignupState.CreatingUserAuthSucceeded -> {
            TransportAppLoadingScreen(
                text = stringResource(id = R.string.adding_user_to_database)
            )
        }
        is SignupState.AddingUserToDatabaseSucceeded -> {
            TransportAppLoadingScreen(
                text = stringResource(id = R.string.successful_signup)
            )
        }
    }


}

@Preview
@Composable
fun SignupScreenPreview(){
    val repository = UserRepository.getInstance()
    val viewModel = SignupViewModel(repository = repository)
    SignUpScreen(viewModel = viewModel)
}
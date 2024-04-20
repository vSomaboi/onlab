package hu.bme.aut.android.transportapp.compose.login

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import hu.bme.aut.android.composenavigation.R
import hu.bme.aut.android.transportapp.data.userdata.UserRepository
import hu.bme.aut.android.transportapp.ui.theme.TopAppBarBackgroundColor
import hu.bme.aut.android.transportapp.viewmodels.LoginViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    onLoginClick: () -> Unit = {},
    onSignupClick: () -> Unit = {}
){

    val state = viewModel.state.collectAsState().value

    LaunchedEffect(key1 = null) {
        viewModel.notifyLoadCompleted()
    }

    Scaffold(
        topBar = {
                 TopAppBar(
                     modifier = Modifier
                         .background(
                             brush = SolidColor(
                                 value = TopAppBarBackgroundColor
                             )
                         ),
                     title = {
                         Text(
                             modifier = Modifier
                                 .fillMaxWidth(),
                             text = stringResource(id = R.string.loginTitle),
                             textAlign = TextAlign.Center,
                             maxLines = 1
                         )
                     },
                     colors = TopAppBarDefaults.topAppBarColors(
                         containerColor = Color.Transparent
                     )
                 )
        },
        content = { paddingValues ->
            ConstraintLayout(modifier = Modifier
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
                val (btnLogin, btnSignup, etEmail, etPassword) = createRefs()

                val glStart = createGuidelineFromStart(0.2f)
                val glEnd = createGuidelineFromEnd(0.2f)
                val margin = dimensionResource(id = R.dimen.button_margin_medium)

                OutlinedTextField(
                    modifier = Modifier
                        .constrainAs(etEmail){
                            start.linkTo(glStart)
                            end.linkTo(glEnd)
                            bottom.linkTo(etPassword.top)
                        },
                    value = viewModel.email,
                    onValueChange = {email -> viewModel.updateEmail(email)},
                    label = {Text(text = stringResource(id = R.string.enter_email_address))}
                )
                OutlinedTextField(
                    modifier = Modifier
                        .constrainAs(etPassword){
                            start.linkTo(glStart)
                            end.linkTo(glEnd)
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                        },
                    value = viewModel.password,
                    onValueChange = {password -> viewModel.updatePassword(password)},
                    label = {Text(text = stringResource(id = R.string.enter_password))},
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
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
                        .constrainAs(btnLogin) {
                            start.linkTo(etPassword.start)
                            end.linkTo(etPassword.end)
                            top.linkTo(
                                anchor = etPassword.bottom,
                                margin = margin
                            )
                            width = Dimension.fillToConstraints
                        },
                    onClick = {
                        viewModel.loginUser(onLoginClick)
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
                ) {
                    Text(
                        text = stringResource(id = R.string.login),
                        fontSize = 20.sp,
                        color = Color.Black
                    )
                }
                TextButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .constrainAs(btnSignup) {
                            start.linkTo(parent.start)
                            bottom.linkTo(
                                anchor = parent.bottom,
                                margin = margin
                            )
                        },
                    onClick = onSignupClick
                ) {
                    Text(
                        text = stringResource(id = R.string.signupTextButtonText),
                        fontSize = 20.sp,
                        color = Color.Black
                    )
                }
            }
        }
    )

}

@Preview
@Composable
fun LoginScreenPreView(){
    val repository = UserRepository.getInstance()
    val viewModel = LoginViewModel(repository = repository)
    LoginScreen(viewModel = viewModel)
}
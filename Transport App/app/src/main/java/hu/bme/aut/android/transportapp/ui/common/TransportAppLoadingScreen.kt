package hu.bme.aut.android.transportapp.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import hu.bme.aut.android.composenavigation.R

@Composable
fun TransportAppLoadingScreen(
    text: String
){
    Scaffold(
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
                val (progressIndicator, tfLoading) = createRefs()
                val margin = dimensionResource(id = R.dimen.margin_between_text_fields)


                CircularProgressIndicator(
                    modifier = Modifier
                        .constrainAs(progressIndicator){
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                        },
                    color = Color.Black
                )
                Text(
                    modifier = Modifier
                        .constrainAs(tfLoading){
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            top.linkTo(
                                anchor = progressIndicator.bottom,
                                margin = margin
                            )
                        },
                    text = text,
                    fontSize = 20.sp
                )
            }
        }
    )
}
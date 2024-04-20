package hu.bme.aut.android.transportapp.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import hu.bme.aut.android.composenavigation.R
import hu.bme.aut.android.transportapp.ui.theme.TopAppBarBackgroundColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarWithProfileIcon(
    text: String,
    onProfileClick: () -> Unit
){
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
                text =  text,
                textAlign = TextAlign.Start,
                maxLines = 1
            )
        },
        actions = {
            IconButton(
                onClick = onProfileClick
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_profile),
                    contentDescription = stringResource(id = R.string.arrowBackDescription)
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent
        )
    )
}

@Composable
@Preview
fun TopAppBarWithProfileIconPreview(){
    TopAppBarWithProfileIcon(text = "Preview") {
        
    }
}
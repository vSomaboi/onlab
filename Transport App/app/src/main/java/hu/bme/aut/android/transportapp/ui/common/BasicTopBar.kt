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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import hu.bme.aut.android.composenavigation.R
import hu.bme.aut.android.transportapp.ui.theme.TopAppBarBackgroundColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BasicTopBar(
    text: String,
    onBackClick: () -> Unit = {}
){
    TopAppBar(
        modifier = Modifier
            .background(
                brush = SolidColor(
                    value = TopAppBarBackgroundColor
                )
            ),
        title = { Text(
            modifier = Modifier
                .fillMaxWidth(),
            text =  text,
            textAlign = TextAlign.Center,
            maxLines = 1,
            fontWeight = FontWeight.Bold
        ) },
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
}
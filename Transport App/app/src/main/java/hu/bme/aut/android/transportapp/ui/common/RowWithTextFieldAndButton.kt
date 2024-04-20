package hu.bme.aut.android.transportapp.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp

@Composable
fun RowWithTextFieldAndButton(
    modifier: Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    onClick: () -> Unit,
    buttonIcon: Painter,
    buttonDescription: String
    ){
    Row(
        modifier = modifier
            .border(
                width = 2.dp,
                brush = SolidColor(Color.Black),
                shape = ButtonDefaults.shape
            )
            .background(
                brush = SolidColor(Color.LightGray),
                shape = ButtonDefaults.shape
            )
            .height(TextFieldDefaults.MinHeight),
        verticalAlignment = Alignment.Top
    ) {
        TextField(
            modifier = Modifier
                .background(
                    SolidColor(Color.Transparent)
                )
                .padding(start = 20.dp),
            value = value,
            onValueChange = onValueChange,
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent
            ),
            singleLine = true
        )
        VerticalDivider(
            thickness = 3.dp,
            color = Color.Black
        )
        IconButton(
            onClick = onClick,
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(start = 14.dp)
        ) {
            Icon(
                painter = buttonIcon,
                contentDescription = buttonDescription
            )
        }
    }
}
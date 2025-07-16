package io.jadu.restrobanc.restrobanc.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.jadu.restrobanc.R
import io.jadu.restrobanc.restrobanc.ui.components.bounceClick
import io.jadu.restrobanc.ui.theme.RestroBancTheme

@Composable
fun Screen() {
    val phoneInput = remember { mutableStateOf("") }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .padding(contentPadding)
                .fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .weight(3f)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "Sooper...",
                    color = Color.White,
                    fontSize = 56.sp
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    "Login to Continue",
                    fontWeight = FontWeight.Black
                )
                Spacer(
                    modifier = Modifier.height(8.dp)
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("+91 7870852837", style = TextStyle())
                    Text("Delete", color = MaterialTheme.colorScheme.onBackground)
                }

                Row(modifier = Modifier.height(16.dp)) { }
                Spacer(
                    modifier = Modifier.weight(1f)
                )
                OutlinedTextField(
                    value = phoneInput.value,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    onValueChange = {
                        phoneInput.value = it
                    },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = {
                        Text(modifier = Modifier, text = "Enter Mobile Number")
                    },
                    singleLine = true,
                    prefix = {
                        Row(
                            modifier = Modifier.padding(end = 8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Image(
                                imageVector = Icons.Default.Person,
                                contentDescription = "India Flag",
                                modifier = Modifier.size(20.dp)
                            )
                            Text("+91")
                            Icon(
                                modifier = Modifier.bounceClick {},
                                imageVector = Icons.Default.KeyboardArrowDown,
                                contentDescription = "Dropdown"
                            )
                            // Vertical Divider
                            Box(
                                modifier = Modifier
                                    .height(24.dp)
                                    .width(1.dp)
                                    .background(Color.Gray)
                            )
                            //Spacer(modifier = Modifier.width(8.dp))
                        }
                    }
                )
            }
        }
    }
}


@Stable
data class Test(
    val id: Int
)

@Composable
@Preview
fun Preview() {
    RestroBancTheme {
        Screen()
    }

}

































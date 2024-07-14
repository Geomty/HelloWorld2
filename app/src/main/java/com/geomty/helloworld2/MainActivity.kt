package com.geomty.helloworld2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.icons.Icons
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.geomty.helloworld2.ui.theme.HelloWorld2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { Content() }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Content(modifier: Modifier = Modifier) {
    var greeting by remember { mutableStateOf("world") }
    var backgroundColor by remember { mutableStateOf(Color.Magenta) }
    var openBottomSheet by remember { mutableStateOf(false) }
    var openDialog by remember { mutableStateOf(false) }
    HelloWorld2Theme {
        Surface(
            color = backgroundColor,
            modifier = modifier
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    text = "Hello ${greeting}!",
                    color = Color.White,
                    fontSize = 52.sp,
                    modifier = modifier
                )
                MainButton("Tap me!") { openBottomSheet = true }
                MainButton("Tap me too!") { openDialog = true }
                FieldRow { greeting = it.ifEmpty { "world" } }
            }
            if (openBottomSheet) {
                ModalBottomSheet({ openBottomSheet = false }, dragHandle = null) {
                    Text(
                        text = "Surprise!",
                        fontSize = 32.sp,
                        modifier = Modifier
                            .padding(top = 160.dp, bottom = 160.dp)
                            .align(Alignment.CenterHorizontally)
                    )
                }
            }
            var radioSelect by remember { mutableStateOf(0) }
            var tempColor by remember { mutableStateOf(Color.Magenta) }
            if (openDialog) {
                AlertDialog({}) {
                    Surface(
                        shape = MaterialTheme.shapes.large,
                        shadowElevation = 10.dp
                    ) {
                        Column(
                            modifier = Modifier.padding(24.dp)
                        ) {
                            Text(
                                text = "Select background color",
                                fontSize = 24.sp
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            ButtonRow("Red", radioSelect == 1) {
                                tempColor = Color.Red
                                radioSelect = 1
                            }
                            ButtonRow("Yellow", radioSelect == 2) {
                                tempColor = Color.Yellow
                                radioSelect = 2
                            }
                            ButtonRow("Green", radioSelect == 3) {
                                tempColor = Color.Green
                                radioSelect = 3
                            }
                            ButtonRow("Cyan", radioSelect == 4) {
                                tempColor = Color.Cyan
                                radioSelect = 4
                            }
                            ButtonRow("Magenta", radioSelect == 5) {
                                tempColor = Color.Magenta
                                radioSelect = 5
                            }
                            ButtonRow("Gray", radioSelect == 6) {
                                tempColor = Color.Gray
                                radioSelect = 6
                            }
                            ButtonRow("Black", radioSelect == 7) {
                                tempColor = Color.Black
                                radioSelect = 7
                            }
                            ButtonRow("Surprise me", radioSelect == 8) {
                                tempColor = Color((1..255).random(), (1..255).random(), (1..255).random())
                                radioSelect = 8
                            }
                            Spacer(modifier = Modifier.height(12.dp))
                            FilledTonalButton(
                                {
                                    openDialog = false
                                    backgroundColor = tempColor
                                },
                                modifier = Modifier.align(Alignment.End)
                            ) {
                                Text(
                                    text = "Save",
                                    fontSize = 16.sp
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MainButton(text: String, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = modifier
    ) {
        Text(
            text = text,
            fontSize = 24.sp
        )
    }
}

@Composable
fun FieldRow(modifier: Modifier = Modifier, onValueChange: (String) -> Unit) {
    var checkedEnabled by remember { mutableStateOf(false) }
    var value by remember { mutableStateOf("") }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Switch(
            checked = checkedEnabled,
            onCheckedChange = {
                checkedEnabled = it
            }
        )
        Spacer(modifier = Modifier.width(16.dp))
        TextField(
            value = value,
            onValueChange = {
                value = it
                onValueChange(it)
            },
            enabled = checkedEnabled,
            label = { Text("Edit name") },
            placeholder = { Text("world") },
            leadingIcon = { Icon(painterResource(R.drawable.wave), null) },
            prefix = { Text("Hello ") },
            suffix = { Text("!") },
            singleLine = true,
            modifier = Modifier.width(200.dp)
        )
    }
}

@Composable
fun ButtonRow(text: String, selected: Boolean, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.selectableGroup()
    ) {
        RadioButton(
            selected = selected,
            onClick = onClick,
            modifier = Modifier.padding(top = 2.dp)
        )
        Text(
            text = text,
            fontSize = 16.sp,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}
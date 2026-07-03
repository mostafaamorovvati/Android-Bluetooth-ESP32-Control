package com.example.esp32project

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HomeScreen(
    bluetoothManager: Esp32BluetoothManager,
    hasPermission: Boolean,
    onPermissionRequest: () -> Unit
) {
    var isConnected by remember { mutableStateOf(false) }
    var isLedOn by remember { mutableStateOf(false) }
    var statusText by remember { mutableStateOf("Ready to Connect") }
    val context = LocalContext.current

    isConnected = bluetoothManager.isConnected

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "ESP32 LED",
            fontSize = 28.sp,
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = statusText,
            fontSize = 18.sp,
            color = if (isConnected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error
        )

        Spacer(modifier = Modifier.height(40.dp))

        if (!hasPermission) {
            Button(onClick = onPermissionRequest) {
                Text("Request Bluetooth Permission")
            }
        } else {
            Button(
                onClick = {
                    statusText = "Connecting..."
                    bluetoothManager.connect { success, msg ->
                        isConnected = success
                        statusText = msg
                        if (!success) {
                            Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
                        }
                    }
                },
                enabled = !isConnected,
                modifier = Modifier.width(220.dp)
            ) {
                Text(if (isConnected) "Connected" else "Connect to ESP32")
            }
        }

        Spacer(modifier = Modifier.height(48.dp))

        Switch(
            checked = isLedOn,
            onCheckedChange = { checked ->
                if (isConnected) {
                    val command = if (checked) '1' else '0'
                    bluetoothManager.sendCommand(command, onError = {
                        isConnected = false
                        isLedOn = false
                        statusText = "Connection Lost!"
                        Toast.makeText(
                            context,
                            "Send Failed! Connection to the device was lost",
                            Toast.LENGTH_SHORT
                        ).show()
                    })
                    isLedOn = checked
                } else {
                    Toast.makeText(context, "Please connect to the ESP32 first", Toast.LENGTH_SHORT)
                        .show()
                }
            },
            enabled = isConnected
        )

        Text(


            text = if (isConnected && isLedOn) "LED On \uD83D\uDFE2" else "LED Off ⚪",
            fontSize = 20.sp,
            modifier = Modifier.padding(top = 12.dp)
        )
    }
}


@Preview(
    showBackground = true
)
@Composable
private fun HomeScreenPreview() {
    val context = LocalContext.current
    val bluetoothManager = Esp32BluetoothManager(context)
    HomeScreen(
        bluetoothManager = bluetoothManager,
        hasPermission = true,
        onPermissionRequest = { }
    )
}

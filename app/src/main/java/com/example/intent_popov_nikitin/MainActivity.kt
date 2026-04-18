package com.example.intent_popov_nikitin

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.intent_popov_nikitin.ui.theme.Intent_Popov_NikitinTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Intent_Popov_NikitinTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    Column {
        Text(
            text = "Hello $name!",
            modifier = modifier
        )

        Button(onClick = {callPhoneNumber("+79001234567",context)}) {
            Text(text = "Позвонить")
        }

        Button(onClick = {sendEmail(context, "support@example.com","Обращение в поддержку","Добрый день,\n")}) {
            Text(text = "Написать email")
        }
        Button(onClick = {showOnMap(context,55.7558, 37.6173,"Кремель" )}) {
            Text(text = "Показать офис на карте")
        }
        Button(onClick = {shareText(context,"Посмотри это приложение: https://play.google.com/...")}) {
            Text(text = "Поделиться контактом")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Intent_Popov_NikitinTheme {
        Greeting("Android")
    }
}

fun callPhoneNumber(phoneNumber: String,context: Context){
    val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phoneNumber"))
    if (intent.resolveActivity(context.packageManager) !=null) {
        context.startActivity(intent)
    }
}

fun sendEmail(context:Context,address:String, subject: String,body:String) {
    val intent=Intent(Intent.ACTION_SENDTO).apply {
data=Uri.parse("mailto:")
        putExtra(Intent.EXTRA_EMAIL,arrayOf(address))
        putExtra(Intent.EXTRA_SUBJECT, subject)
        putExtra(Intent.EXTRA_TEXT, body)
    }
    if (intent.resolveActivity(context.packageManager) != null) {
        context.startActivity(intent)
    }
}

fun shareText(context:Context,text: String) {
    val sendIntent =Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, text)
    }
    val chooser=Intent.createChooser(sendIntent, "Поделиться через...")
    context.startActivity(chooser)
}
fun showOnMap(context:Context,latitude: Double, longitude: Double, label: String) {
    val geoUri =Uri.parse("geo:0,0?q=$latitude,$longitude($label)")
    val intent = Intent(Intent.ACTION_VIEW,geoUri)
    if (intent.resolveActivity(context.packageManager) !=null) {
context.startActivity(intent)
    }
}
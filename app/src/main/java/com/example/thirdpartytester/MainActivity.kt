package com.example.thirdpartytester

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat.startActivityForResult
import com.example.thirdpartytester.ui.theme.ThirdPartyTesterTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ThirdPartyTesterTheme {
                // A surface container using the 'background' color from the theme
                DemoScreen()
            }
        }
    }
}

@Composable
fun DemoScreen() {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        backgroundColor = MaterialTheme.colors.background,
        topBar = {
            // top app bar with person info icon and settings icon
            TopBar()
        },
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                OrthosButton()
            }
        }
    }
}

@Composable
fun TopBar() {
    // on the left the personal info icon and on the right the settings icon
    TopAppBar(
        backgroundColor = MaterialTheme.colors.primary,
        contentColor = MaterialTheme.colors.onPrimary,
        content = {
            Spacer(modifier = Modifier.weight(1f))
            Text(text = "Demo", style = MaterialTheme.typography.h4)
            Spacer(modifier = Modifier.weight(1f))
        }
    )
}

@Composable
fun OrthosButton() {
    val textStyle = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        letterSpacing = 1.25.sp
    )
    val context = LocalContext.current as Activity
    val size = 48.dp
    Button(
        onClick = { orthosButtonOnClick(context) },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.White,
            contentColor = MaterialTheme.colors.primary
        ),
        border = BorderStroke(2.dp, MaterialTheme.colors.primary),
        elevation = ButtonDefaults.elevation(0.dp, 0.dp),
        modifier = Modifier
            .padding(16.dp)
            .height(size),
        shape = RoundedCornerShape(100),
        contentPadding = PaddingValues(end = 16.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.icon_logo_v2),
            contentDescription = "Orthos Logo",
            modifier = Modifier
                .border(1.dp, MaterialTheme.colors.primary, CircleShape),
        )

        Text(
            text = "Open Orthos",
            style = textStyle,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}


fun orthosButtonOnClick(activity: Activity) {
    val processToken = "97adb27c-6778-4aa5-b6fb-2ec635859cfb"
    val tokenURL = Uri.parse("https://orthos.app/qr/token?=$processToken")
    val launchIntent: Intent? =
        activity.packageManager.getLaunchIntentForPackage("com.orthos.app.android")
    launchIntent?.putExtra(Intent.ACTION_SEND, "testtest")
    launchIntent?.data = tokenURL
    if (launchIntent != null)
        activity.applicationContext.startActivity(launchIntent)
    else
        Log.d("orthos button click", "Orthos not installed")
    // TODO: tell user to download orthos app
}
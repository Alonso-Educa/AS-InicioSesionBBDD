package com.example.bbdd

//import androidx.compose.material3.*

//import java.time.format.TextStyle
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.text.input.*
import com.example.bbdd.navigation.AppNavigation
import com.example.bbdd.screens.Formulario
import com.example.bbdd.ui.theme.BBDDTheme
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BBDDTheme {
                AppNavigation()
            }
        }
    }
}


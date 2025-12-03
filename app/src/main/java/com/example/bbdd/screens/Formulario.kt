package com.example.bbdd.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextObfuscationMode
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedSecureTextField
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.room.Room

import com.example.bbdd.localdb.AppDB
import com.example.bbdd.localdb.Estructura
import com.example.bbdd.localdb.UsuarioDao
import com.example.bbdd.localdb.UsuariosData
import com.example.bbdd.navigation.AppScreens
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Formulario(navController: NavController) {
    // Variables de los datos recogidos en los formularios
    var nombre by remember { mutableStateOf("") }
    var apellidos by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var incorporacion by remember { mutableStateOf("") }
    var contrasena = rememberTextFieldState("")
    var passVisible by remember { mutableStateOf(false) }
    val emailPattern =
        Regex("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}") // Patrón a seguir en el email
    // Se obtiene el contexto actual, necesario para construir la base de datos Room y mostrar mensajes Toasts.
    val context = LocalContext.current
    // Se crea una instancia de la base de datos local, indicando el contexto, la clase base de datos y el nombre del archivo,
    // permitiendo consultas en el hilo principal.
    val db = Room.databaseBuilder(context, AppDB::class.java, Estructura.DB.NAME)
        .allowMainThreadQueries().build()
    // La aplicación está corriendo en un hilo, pero cuando nosotros estamos almacenando los datos, estos datos corren en un hilo
    // distinto al que está corriendo la aplicación y eso no es la mejor solución para producción.
    // Se soluciona temporalmente con .allowMainThreadQueries() y si no, es necesario realizar corrutinas o hilos en background.
    // Obtención del DAO, es decir, la interfaz para realizar operaciones CRUD sobre la tabla.
    val usuarioD: UsuarioDao = db.usuarioDao()
    var usuarioem: UsuariosData?
    val dbfire = FirebaseFirestore.getInstance()
    // Crea una instancia de acceso a la base de datos Firestore. Devuelve una instancia del cliente de Firestore asociada con la
    // aplicación. Al guardarla en dbfire, se puede utilizar para realizar operaciones como acceder a colecciones, documentos, insertar o
    // consultar datos.
    lateinit var auth: FirebaseAuth
    auth = Firebase.auth


// BARRA SUPERIOR
    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.height(60.dp), title = {
                    Text(text = "Registrar un usuario", fontSize = 15.sp)
                }, colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White
                )
            )
        }) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Pedimos el nombre
            OutlinedTextField(
                value = nombre, onValueChange = {
                    if (it.length < 40) {
                        nombre = it
                    }
                }, label = { Text("Nombre del usuario") }, modifier = Modifier.width(300.dp)
            )
            //Pedimos los apellidos
            OutlinedTextField(
                value = apellidos, onValueChange = {
                    if (it.length < 50) {
                        apellidos = it
                    }
                }, label = { Text("Apellidos del usuario") }, modifier = Modifier.width(300.dp)
            )
            // Pedimos el email
            OutlinedTextField(
                value = email, onValueChange = {
                    if (it.length < 40) {
                        email = it
                    }
                }, label = { Text("Email") }, modifier = Modifier.width(300.dp)
            )
            //Pedimos la contraseña
            OutlinedSecureTextField(
                state = contrasena,
                label = { Text("Contraseña") },
                modifier = Modifier.width(300.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                trailingIcon = {
                    IconButton(onClick = { passVisible = !passVisible }) {
                        Icon(
                            imageVector = if (passVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                            contentDescription = if (passVisible) "Ocultar contraseña" else "Mostrar contraseña"
                        )
                    }
                },
                textObfuscationMode = if (passVisible) TextObfuscationMode.Visible else TextObfuscationMode.RevealLastTyped
            )
//Pedimos la incorporación
            OutlinedTextField(
                value = incorporacion,
                onValueChange = { incorporacion = it },
                label = { Text("Incorporación del usuario") },
                modifier = Modifier.width(300.dp)
            )
            Spacer(Modifier.size(20.dp))
            Button(
                onClick = {
                    usuarioem = db.usuarioDao()
                        .getUnUser(email) // Obtiene el usuario completo en función del email a registrar.
                    // Validaciones básicas de campos
                    when {
                        nombre.isBlank() -> {
                            Toast.makeText(
                                context,
                                "El nombre no puede estar vacío",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        apellidos.isBlank() -> {
                            Toast.makeText(
                                context,
                                "Los apellidos no pueden estar vacíos",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        email.isBlank() -> {
                            Toast.makeText(
                                context,
                                "El email no puede estar vacío",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        !email.matches(emailPattern) -> {
                            Toast.makeText(
                                context,
                                "El email no tiene un formato válido",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        contrasena.text.length <= 3 -> {
                            Toast.makeText(
                                context,
                                "La contraseña debe tener al menos 3 caracteres",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        incorporacion.isBlank() -> {
                            Toast.makeText(
                                context,
                                "La fecha de incorporación no puede estar vacía",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        usuarioem != null -> {
                            Toast.makeText(
                                context,
                                "Ya existe alguien registrado con ese email",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        else -> {
                            // Crear usuario con email y contraseña para firebase, en base a lo introducido al registrarse
                            auth.createUserWithEmailAndPassword(email, contrasena.text.toString())
                                .addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        val uid =
                                            auth.currentUser?.uid ?: return@addOnCompleteListener
                                        // Guardar bd local
                                        val usr = UsuariosData(
                                            nombreUsuario = nombre,
                                            apellidosUsuario = apellidos,
                                            incorporacionUsuario = incorporacion,
                                            email = email
                                        )
                                        usuarioD.nuevoUsuario(usr)
                                        // Guardar en la nube Firestore
                                        val data = mapOf(
                                            "nombre" to nombre,
                                            "apellidos" to apellidos,
                                            "email" to email,
                                            "incorporacion" to incorporacion
                                        )
                                        dbfire.collection("usuarios").document(uid).set(data)
                                            .addOnSuccessListener {
                                                Toast.makeText(
                                                    context,
                                                    "Usuario registrado correctamente",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                                navController.navigate(route = AppScreens.Resultados.route)
                                            }
                                            .addOnFailureListener { e ->
                                                Toast.makeText(
                                                    context,
                                                    "Error de Firestore: ${e.message}",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }
                                    } else {
                                        // Log específico para debug
                                        Log.e(
                                            "Registro",
                                            "Firebase error: ${task.exception?.message}"
                                        )
                                        Toast.makeText(
                                            context,
                                            "Error Firebase: ${task.exception?.message}",
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }
                                }
                        }
                    }
                }
            ) {
                Text(text = "Agregar usuario")
            }
            Button(
                onClick = {
                    Toast.makeText(context, "Ir a resultados", Toast.LENGTH_SHORT).show()
                    navController.navigate(route = AppScreens.Resultados.route)
                }) {
                Text(text = "Ir a resultados")
            }
            Button(
                onClick = {
                    Toast.makeText(context, "Iniciar sesión", Toast.LENGTH_SHORT).show()
                    navController.navigate(route = AppScreens.Inicio.route)
                }) {
                Text(text = "Iniciar sesión")
            }
        }
    }
}

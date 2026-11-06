package com.example.examen_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.gms.maps.model.*
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import androidx.compose.ui.tooling.preview.Preview

data class Lenguaje(val nombre: String, val imagen: Int)

val Verde = Color(0xFF2E7D32)
val VerdeClaro = Color(0xFFE3F4E5)

val lenguajes = listOf(
    Lenguaje("Kotlin", R.drawable.kot),
    Lenguaje("Java", R.drawable.java),
    Lenguaje("Python", R.drawable.py),
    Lenguaje("JavaScript", R.drawable.js),
    Lenguaje("PHP", R.drawable.php),
    Lenguaje("C#", R.drawable.cs),
    Lenguaje("HTML", R.drawable.html),
    Lenguaje("CSS", R.drawable.css),
    Lenguaje("SQL", R.drawable.sql),
    Lenguaje("C++", R.drawable.cpp),
)

const val NOMBRE = "Farid Garcia"
const val CORREO = "faridgmtz@gmail.com"
const val ACERCA_DE = "Me considero una persona aplicada y dedicada a la carrera de Tecnologias de la Informacion e Innovacion Digital"
val UNIVERSIDAD = LatLng(19.979854713814724, -98.68471321973838)
const val NOMBRE_UNIVERSIDAD = "Universidad Politécnica de Pachuca"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme(colorScheme = lightColorScheme()) {
                PerfilScreen()
            }
        }
    }
}

@Composable
fun PerfilContenido(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        CabeceraPerfil()

        Spacer(Modifier.height(12.dp))

        Column(Modifier.padding(horizontal = 16.dp)) {
            Text(NOMBRE, fontSize = 28.sp, fontWeight = FontWeight.Bold)
            Text(CORREO, fontSize = 15.sp, color = Verde, fontWeight = FontWeight.Medium)
            Spacer(Modifier.height(12.dp))
            Text("Acerca de", fontSize = 18.sp, fontWeight = FontWeight.SemiBold, color = Verde)
            Spacer(Modifier.height(4.dp))
            Text(ACERCA_DE, fontSize = 15.sp, color = Color.DarkGray)
        }

        Spacer(Modifier.height(20.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "LENGUAJES DE PROGRAMACIÓN",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = Verde
            )
            Text("Desliza →", fontSize = 13.sp, color = Color.Gray)
        }
        Spacer(Modifier.height(10.dp))
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(lenguajes) { lenguaje ->
                EtiquetaLenguaje(lenguaje)
            }
        }

        Spacer(Modifier.height(20.dp))

        Text(
            "MI UNIVERSIDAD",
            modifier = Modifier.padding(horizontal = 16.dp),
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            color = Verde
        )
        Spacer(Modifier.height(10.dp))
        MapaUniversidad()

        Spacer(Modifier.height(16.dp))
    }
}

@Composable
fun CabeceraPerfil() {
    Box(modifier = Modifier.fillMaxWidth()) {
        Image(
            painter = painterResource(R.drawable.portada),
            contentDescription = "Portada",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .clip(RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp))
        )
        Image(
            painter = painterResource(R.drawable.fotop),
            contentDescription = "Foto de perfil",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 16.dp)
                .offset(y = 50.dp)
                .size(100.dp)
                .clip(CircleShape)
                .border(4.dp, MaterialTheme.colorScheme.surface, CircleShape)
        )
    }
    Spacer(Modifier.height(50.dp))
}

@Composable
fun EtiquetaLenguaje(lenguaje: Lenguaje) {
    Card(
        shape = CircleShape,
        colors = CardDefaults.cardColors(
            containerColor = VerdeClaro,
            contentColor = Color.Black
        )
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(lenguaje.imagen),
                contentDescription = lenguaje.nombre,
                modifier = Modifier.size(28.dp)
            )
            Spacer(Modifier.width(8.dp))
            Text(lenguaje.nombre, fontSize = 15.sp, fontWeight = FontWeight.Medium)
        }
    }
}

@Composable
fun MapaUniversidad() {
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(UNIVERSIDAD, 16f)
    }
    GoogleMap(
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp)
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(20.dp)),
        cameraPositionState = cameraPositionState
    ) {
        Marker(
            state = MarkerState(position = UNIVERSIDAD),
            title = NOMBRE_UNIVERSIDAD
        )
    }
}

@Composable
fun PerfilScreen() {
    var mostrarDialogo by remember { mutableStateOf(false) }

    Scaffold(
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Snackbar(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(20.dp),
                    containerColor = VerdeClaro,
                    contentColor = Color.Black
                ) {
                    Text("Escribe aquí tu mensaje")
                }
                Spacer(Modifier.width(12.dp))
                FloatingActionButton(
                    onClick = { mostrarDialogo = true },
                    shape = CircleShape,
                    containerColor = Verde,
                    contentColor = Color.White
                ) {
                    Text("+", fontSize = 24.sp)
                }
            }
        }
    ) { paddingValues ->
        PerfilContenido(Modifier.padding(paddingValues))
    }

    if (mostrarDialogo) {
        AlertDialog(
            onDismissRequest = { mostrarDialogo = false },
            title = { Text("Botón presionado") },
            text = { Text("Este FAB solo es de adorno :)") },
            confirmButton = {
                TextButton(onClick = { mostrarDialogo = false }) { Text("OK") }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PerfilPreview() {
    MaterialTheme { PerfilScreen() }
}
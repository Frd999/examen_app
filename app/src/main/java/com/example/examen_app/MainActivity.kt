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

val lenguajes = listOf(
    Lenguaje("Kotlin", R.drawable.kotlin),
    Lenguaje("Java", R.drawable.java),
    Lenguaje("Python", R.drawable.python),
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
const val ACERCA_DE = "Me considero una persona apta para la resolucion de problemas..."
val UNIVERSIDAD = LatLng(19.979854713814724, -98.68471321973838)
const val NOMBRE_UNIVERSIDAD = "Universidad Politécnica de Pachuca"
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
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
            Text(NOMBRE, fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Text(CORREO, fontSize = 14.sp)
            Spacer(Modifier.height(12.dp))
            Text("Acerca de", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
            Spacer(Modifier.height(4.dp))
            Text(ACERCA_DE, fontSize = 15.sp)
        }

        Spacer(Modifier.height(16.dp))

        Text(
            "Lenguajes que manejo",
            modifier = Modifier.padding(horizontal = 16.dp),
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(Modifier.height(8.dp))
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(lenguajes) { lenguaje ->
                EtiquetaLenguaje(lenguaje)
            }
        }

        Spacer(Modifier.height(16.dp))

        Text(
            "Mi universidad",
            modifier = Modifier.padding(horizontal = 16.dp),
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(Modifier.height(8.dp))
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
            modifier = Modifier.fillMaxWidth().height(180.dp)
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
    Card(shape = RoundedCornerShape(12.dp)) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(lenguaje.imagen),
                contentDescription = lenguaje.nombre,
                modifier = Modifier.size(56.dp)
            )
            Spacer(Modifier.height(4.dp))
            Text(lenguaje.nombre, fontSize = 13.sp)
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
            .padding(horizontal = 16.dp),
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
                Snackbar(modifier = Modifier.weight(1f)) {
                    Text("Escribe aquí tu mensaje")
                }
                Spacer(Modifier.width(12.dp))
                FloatingActionButton(onClick = { mostrarDialogo = true }) {
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
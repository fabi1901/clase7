package com.example.contactos

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.contactos.ui.theme.ContactosTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ContactosTheme {
                    AplicacionDeContactos()
                }
            }
        }
    }

enum class MetodoContactoPreferido {
    TELEFONO, EMAIL
}

data class Contacto(
    val nombre: String,
    val direccion: String,
    val telefono: String,
    val email: String,
    val metodoPreferido: MetodoContactoPreferido
)

@Composable
fun AplicacionDeContactos() {

    var contactos by remember { mutableStateOf(listOf<Contacto>()) }
    var nombre by remember { mutableStateOf("") }
    var direccion by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var metodoPreferido by remember { mutableStateOf(MetodoContactoPreferido.TELEFONO) }

    Column(modifier = Modifier.padding(16.dp)) {
        FormularioDeContacto(
            nombre = nombre,
            direccion = direccion,
            telefono = telefono,
            email = email,
            metodoPreferido = metodoPreferido,
            onNombreChange = { nombre = it },
            onDireccionChange = { direccion = it },
            onTelefonoChange = { telefono = it },
            onEmailChange = { email = it },
            onMetodoPreferidoChange = { metodoPreferido = it },
            onAgregarContacto = {
                val nuevoContacto = Contacto(nombre, direccion, telefono, email, metodoPreferido)
                contactos = contactos + nuevoContacto
                nombre = ""
                direccion = ""
                telefono = ""
                email = ""
                metodoPreferido = MetodoContactoPreferido.TELEFONO
            }
        )

        Spacer(modifier = Modifier.height(16.dp))
        ListaDeContactos(contactos = contactos)
    }
}

@Composable
fun ListaDeContactos(contactos: List<Contacto>) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(contactos) { contacto ->
            TarjetaDeContacto(contacto = contacto)
        }
    }
}



@Composable
fun FormularioDeContacto(
    nombre: String,
    direccion: String,
    telefono: String,
    email: String,
    metodoPreferido: MetodoContactoPreferido,
    onNombreChange: (String) -> Unit,
    onDireccionChange: (String) -> Unit,
    onTelefonoChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onMetodoPreferidoChange: (MetodoContactoPreferido) -> Unit,
    onAgregarContacto: () -> Unit
) {
    Column {
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            "Ingrese un contacto",
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = nombre,
            onValueChange = onNombreChange,
            label = { Text("Nombre") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = direccion,
            onValueChange = onDireccionChange,
            label = { Text("Direccion") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = telefono,
            onValueChange = onTelefonoChange,
            label = { Text("Telefono") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = email,
            onValueChange = onEmailChange,
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))
        Column {
            Text("Método de contacto preferido:")
            Spacer(modifier = Modifier.width(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = metodoPreferido == MetodoContactoPreferido.TELEFONO,
                    onClick = { onMetodoPreferidoChange(MetodoContactoPreferido.TELEFONO) }
                )
                Text("Teléfono")
                Spacer(modifier = Modifier.width(8.dp))
                RadioButton(
                    selected = metodoPreferido == MetodoContactoPreferido.EMAIL,
                    onClick = { onMetodoPreferidoChange(MetodoContactoPreferido.EMAIL) }
                )
                Text("Email")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = onAgregarContacto,
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Agregar Contacto")
        }




    }
}

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun TarjetaDeContacto(contacto: Contacto) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp).fillMaxWidth()
        ) {
            Text(
                text = contacto.nombre,
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(modifier = Modifier.height(8.dp))
            BoxWithConstraints(
                modifier = Modifier.fillMaxWidth().height(120.dp)
            ) {
                val anchoPantalla = maxWidth
                LazyRow(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    item { InfoContacto(titulo = "Dirección", valor = contacto.direccion, ancho = anchoPantalla) }
                    item { InfoContacto(titulo = "Teléfono", valor = contacto.telefono, ancho = anchoPantalla) }
                    item { InfoContacto(titulo = "Email", valor = contacto.email, ancho = anchoPantalla) }
                }
            }



                Spacer(modifier = Modifier.height(8.dp))
                Text("Método preferido: ${if (contacto.metodoPreferido == MetodoContactoPreferido.TELEFONO) "Teléfono" else "Email"}")
            }
        }
    }


@Composable
fun InfoContacto(titulo: String, valor: String, ancho: androidx.compose.ui.unit.Dp) {
    Column(
        modifier = Modifier
            .width(ancho)
            .fillMaxWidth()
            .padding(end = 32.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = titulo, style = MaterialTheme.typography.titleSmall)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = valor, style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(16.dp))
    }
}






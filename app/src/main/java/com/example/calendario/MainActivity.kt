package com.example.calendario

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Buscar el botón "Comenzar"
        val btnComenzar: Button = findViewById(R.id.button)

        // Configurar el evento de clic del botón
        btnComenzar.setOnClickListener {
            // Al presionar el botón, redirigir a MainActivity2 (Calendario)
            val intent = Intent(this, MainActivity2::class.java)
            startActivity(intent)
        }
    }
}

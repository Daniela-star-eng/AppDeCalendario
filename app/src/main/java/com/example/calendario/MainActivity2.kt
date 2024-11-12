package com.example.calendario

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*

class MainActivity2 : AppCompatActivity() {
    private lateinit var calendarView: CalendarView
    private lateinit var btnAddEvent: Button
    private lateinit var eventListView: ListView
    private val eventsMap = mutableMapOf<String, MutableList<String>>() // Mapa de eventos
    private val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    private var selectedDate: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        calendarView = findViewById(R.id.calendarView)
        btnAddEvent = findViewById(R.id.btnAddEvent)
        eventListView = findViewById(R.id.eventListView)

        // Configurar la fecha seleccionada inicial
        selectedDate = dateFormat.format(Date(calendarView.date))

        // Detectar cambio de fecha
        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            selectedDate = dateFormat.format(Calendar.getInstance().apply {
                set(year, month, dayOfMonth)
            }.time)
            updateEventList() // Actualiza la lista de eventos al cambiar la fecha
        }

        // Botón para agregar un evento
        btnAddEvent.setOnClickListener {
            addEventForSelectedDate()
        }

        updateEventList() // Inicializa la lista de eventos al iniciar la actividad
    }

    private fun addEventForSelectedDate() {
        // Crear un cuadro de diálogo para ingresar el evento
        val editText = EditText(this)
        editText.hint = "Descripción del evento"
        val dialog = AlertDialog.Builder(this)
            .setTitle("Agregar Evento")
            .setView(editText)
            .setPositiveButton("Agregar") { _, _ ->
                val eventText = editText.text.toString()
                if (eventText.isNotBlank()) {
                    // Agregar evento al mapa
                    if (!eventsMap.containsKey(selectedDate)) {
                        eventsMap[selectedDate] = mutableListOf()
                    }
                    eventsMap[selectedDate]?.add(eventText)
                    updateEventList() // Actualiza la lista después de agregar el evento
                    Toast.makeText(this, "Evento agregado", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Por favor ingresa un evento", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancelar", null)
            .create()
        dialog.show()
    }

    // Función para actualizar la lista de eventos en la interfaz
    private fun updateEventList() {
        // Obtener los eventos para la fecha seleccionada
        val events = eventsMap[selectedDate] ?: emptyList()

        // Usar un ArrayAdapter para mostrar los eventos en la ListView
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, events)
        eventListView.adapter = adapter
    }
}

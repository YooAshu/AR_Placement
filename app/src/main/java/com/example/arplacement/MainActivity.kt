package com.example.arplacement

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton

class MainActivity : AppCompatActivity() {

    private val drills = listOf("Drill 1", "Drill 2", "Drill 3")
    private lateinit var spinner: Spinner
    private lateinit var startButton: ExtendedFloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        spinner = findViewById(R.id.spinner)
        startButton = findViewById(R.id.startDrillButton)

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, drills)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        startButton.setOnClickListener {
            val selectedDrill = spinner.selectedItem.toString()
            val intent = Intent(this, ARActivity::class.java)
            intent.putExtra("drillName", selectedDrill)
            startActivity(intent)
        }
    }
}

package com.example.daily_conversor

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText

class Result_Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_result)

        val edtMassa: TextInputEditText = findViewById(R.id.edt_massa)
        val edtDistancia: TextInputEditText = findViewById(R.id.edt_distancia)
        val edtVolume: TextInputEditText = findViewById(R.id.edt_volume)

        val tvResultado: TextView = findViewById(R.id.tv_resultado)

        val btnLimpar: Button = findViewById(R.id.btn_limpar)
        val btnCalcular: Button = findViewById(R.id.btn_Calcular)

        val spinnerMassaIn: Spinner = findViewById(R.id.spinner_massa_input)
        val spinnerMassaOut: Spinner = findViewById(R.id.spinner_massa_output)

        val spinnerDistanciaIn: Spinner = findViewById(R.id.spinner_distancia_input)
        val spinnerDistanciaOut: Spinner = findViewById(R.id.spinner_distancia_output)

        val spinnerVolumeIn: Spinner = findViewById(R.id.spinner_volume_input)
        val spinnerVolumeOut: Spinner = findViewById(R.id.spinner_volume_output)





        val adapterMassa = ArrayAdapter.createFromResource(
            this, R.array.mass_conversion_table,
            android.R.layout.simple_spinner_item
        )
        adapterMassa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerMassaIn.adapter = adapterMassa
        spinnerMassaOut.adapter = adapterMassa

        val adapterDistancia = ArrayAdapter.createFromResource(
            this, R.array.distance_conversion_table,
            android.R.layout.simple_spinner_item
        )
        adapterDistancia.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerDistanciaIn.adapter = adapterDistancia
        spinnerDistanciaOut.adapter = adapterDistancia

        val adapterCapacity = ArrayAdapter.createFromResource(
            this, R.array.capacity_conversion_table,
            android.R.layout.simple_spinner_item
        )
        adapterCapacity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerVolumeIn.adapter = adapterCapacity
        spinnerVolumeOut.adapter = adapterCapacity

        btnLimpar.setOnClickListener {
            edtMassa.text?.clear()
            edtVolume.text?.clear()
            edtDistancia.text?.clear()
            tvResultado.text = ""
        }

        btnCalcular.setOnClickListener {
            val massaText = edtMassa.text.toString()
            val distanciaText = edtDistancia.text.toString()
            val volumeText = edtVolume.text.toString()

            if (massaText.isEmpty() && distanciaText.isEmpty() && volumeText.isEmpty()) {
                Snackbar.make(edtMassa, "Preencha pelo menos um campo para calcular", Snackbar.LENGTH_LONG).show()
            } else {

                if (massaText.isNotEmpty()) {
                    val massa = massaText.toDouble()
                    val fromUnitMassa = spinnerMassaIn.selectedItem.toString()
                    val toUnitMassa = spinnerMassaOut.selectedItem.toString()
                    val resultMassa = convertUnits(massa, fromUnitMassa, toUnitMassa, "massa")
                    tvResultado.text = resultMassa?.let { "$it $toUnitMassa" }
                }


                if (distanciaText.isNotEmpty()) {
                    val distancia = distanciaText.toDouble()
                    val fromUnitDistancia = spinnerDistanciaIn.selectedItem.toString()
                    val toUnitDistancia = spinnerDistanciaOut.selectedItem.toString()
                    val resultDistancia = convertUnits(distancia, fromUnitDistancia, toUnitDistancia, "distancia")
                   tvResultado.text = resultDistancia?.let { "$it $toUnitDistancia" }
                }


                if (volumeText.isNotEmpty()) {
                    val volume = volumeText.toDouble()
                    val fromUnitVolume = spinnerVolumeIn.selectedItem.toString()
                    val toUnitVolume = spinnerVolumeOut.selectedItem.toString()
                    val resultVolume = convertUnits(volume, fromUnitVolume, toUnitVolume, "volume")
                    tvResultado.text = resultVolume?.let { "$it $toUnitVolume" }
                }
            }
        }
    }

    private fun convertUnits(value: Double, fromUnit: String, toUnit: String, type: String): Double? {

        val conversionFactors = when (type) {
            "massa" -> mapOf(
                "Tonelada" to 1_000_000.0,
                "Quilograma" to 1_000.0,
                "Grama" to 1.0,
                "Miligrama" to 0.001
            )
            "distancia" -> mapOf(
                "Quilômetro" to 1_000.0,
                "Metro" to 1.0,
                "Centímetro" to 0.01,
                "Milímetro" to 0.001
            )
            "volume" -> mapOf(
                "Litro" to 1.0,
                "Mililitro" to 0.001,
                "Cúbico" to 1_000.0
            )
            else -> null
        }


        return if (conversionFactors != null) {
            val fromFactor = conversionFactors[fromUnit]
            val toFactor = conversionFactors[toUnit]
            if (fromFactor != null && toFactor != null) {
                (value * fromFactor) / toFactor
            } else {
                null
            }
        } else {
            null
        }
    }
}

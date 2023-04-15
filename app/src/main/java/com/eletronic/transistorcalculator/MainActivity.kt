package com.eletronic.transistorcalculator

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private lateinit var etIb: EditText
    private lateinit var etIc: EditText
    private lateinit var etIe: EditText
    private lateinit var etVce: EditText
    private lateinit var etPower: EditText
    private lateinit var tvSaturation: TextView

    fun calculateResults(){
        var Vbb: Float; var Vcc: Float; var Rb: Float; var Rc: Float; var gain: Float
        var saturated = false
        try {
            Vbb = findViewById<EditText>(R.id.etVbb).text.toString().toFloat()
            Vcc = findViewById<EditText>(R.id.etVcc).text.toString().toFloat()
            Rb = findViewById<EditText>(R.id.etRb).text.toString().toFloat()
            Rc = findViewById<EditText>(R.id.etRc).text.toString().toFloat()
            gain = findViewById<EditText>(R.id.etGain).text.toString().toFloat()
        } catch (e: NumberFormatException){
            return
        }

        if (Rb <= 0 || Rc <= 0){
            return
        }
        var Ib = (Vbb - 0.7) / (Rb * 1000) // Rb comes in kiloOhms, converting to Ohms
        var Ic_max = Vcc / Rc
        var Ic = gain * Ib
        if (Ic > Ic_max){
            Ic = Ic_max.toDouble()
            saturated = true
        }
        var Ie = Ib + Ic
        var Vce = Vcc - Rc*Ic
        var power = Vce * Ic

        etIb.setText(String.format("%.2f", Ib * 1000000)) // microAmpere
        etIc.setText(String.format("%.2f", Ic * 1000)) // miliAmpere
        etIe.setText(String.format("%.2f", Ie * 1000)) // miliAmpere
        etVce.setText(String.format("%.2f", Vce))
        etPower.setText(String.format("%.2f", power * 1000)) // miliWatt
        if (saturated){
            tvSaturation.setText("SATURADO")
            tvSaturation.setTextColor(Color.RED)
        } else {
            tvSaturation.setText("NÃO-SATURADO")
            tvSaturation.setTextColor(Color.BLUE)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etIb = findViewById<EditText>(R.id.etIb)
        etIc = findViewById<EditText>(R.id.etIc)
        etIe = findViewById<EditText>(R.id.etIe)
        etVce = findViewById<EditText>(R.id.etVce)
        etPower = findViewById<EditText>(R.id.etPower)
        tvSaturation = findViewById<TextView>(R.id.tvSaturation)
        tvSaturation.setTextColor(Color.BLUE)

        findViewById<Button>(R.id.btnCalculate).setOnClickListener {
            calculateResults()
        }

    }
}
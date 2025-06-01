package com.example.personalityquiz

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.os.SystemClock
import android.view.View
import android.widget.*
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private var introvert = 0
    private var extrovert = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val mainLayout = findViewById<ScrollView>(R.id.main)

        ViewCompat.setOnApplyWindowInsetsListener(mainLayout) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val chronometer = findViewById<Chronometer>(R.id.myChronometr)
        chronometer.base = SystemClock.elapsedRealtime()
        chronometer.start()

        val radioGroup = findViewById<RadioGroup>(R.id.LikePeople)
        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            if (checkedId == R.id.YesLikePople) {
                extrovert++
            } else {
                introvert++
            }
        }

        val datePicker = findViewById<DatePicker>(R.id.my_calendar_datepicker)
        val day = datePicker.dayOfMonth
        val month = datePicker.month + 1
        val year = datePicker.year
        val selectedDate = "$day.$month.$year"

        val PartyCheckBox = findViewById<CheckBox>(R.id.PartyCheckBox)
        val ConcertsCheckBox = findViewById<CheckBox>(R.id.ConcertsCheckBox)
        val PublickCheckBox = findViewById<CheckBox>(R.id.PublickCheckBox)
        val ForestCheckBox = findViewById<CheckBox>(R.id.ForestCheckBox)
        val BooksCheckBox = findViewById<CheckBox>(R.id.BooksCheckBox)
        val FilmsCheckBox = findViewById<CheckBox>(R.id.FilmsCheckBox)

        if (PartyCheckBox.isChecked) extrovert++
        if (ConcertsCheckBox.isChecked) extrovert++
        if (PublickCheckBox.isChecked) extrovert++
        if (ForestCheckBox.isChecked) introvert++
        if (BooksCheckBox.isChecked) introvert++
        if (FilmsCheckBox.isChecked) introvert++

        val seekBar = findViewById<SeekBar>(R.id.my_seekbar)
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                when (progress) {
                    0 -> introvert += 2
                    1 -> introvert++
                    3 -> extrovert++
                    4 -> extrovert += 2
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })

        val colors = arrayOf("Czerwony", "Niebieski", "Zielony", "Żółty")
        val colorValues = arrayOf(
            0xFFFF0000.toInt(),   // czerwony
            0xFF2196F3.toInt(),   // niebieski
            0xFF4CAF50.toInt(),   // zielony
            0xFFFFEB3B.toInt()    // żółty
        )

        val spinner = findViewById<Spinner>(R.id.spinner)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, colors)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        spinner.setSelection(1)
        mainLayout.setBackgroundColor(colorValues[1])
        introvert++

        spinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?, view: View?, position: Int, id: Long
            ) {
                mainLayout.setBackgroundColor(colorValues[position])
                when (colors[position]) {
                    "Czerwony" -> extrovert++
                    "Niebieski" -> introvert++
                    "Zielony" -> extrovert++
                    "Żółty" -> introvert++
                }
                Toast.makeText(
                    this@MainActivity,
                    "Wybrałeś kolor: ${colors[position]}",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        val timerText = findViewById<TextView>(R.id.TimerText)
        val endButton = findViewById<Button>(R.id.endButton)


        val countDownTimer = object : CountDownTimer(100_000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val secondsLeft = millisUntilFinished / 1000
                timerText.text = "Pozostało $secondsLeft sekund"
            }

            override fun onFinish() {
                timerText.text = "Czas się skończył"
                val intent = Intent(this@MainActivity, TimeOutActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
        countDownTimer.start()

        endButton.setOnClickListener {
            chronometer.stop()
            countDownTimer.cancel()
        }
    }
}

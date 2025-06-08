package com.example.personalityquiz

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SummaryActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_summary)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets

        }
        val MytextView = findViewById<TextView>(R.id.personality)
        val dateTextView = findViewById<TextView>(R.id.date)
        val MyImage = findViewById<ImageView>(R.id.MyImage)

        val introvert = intent.getIntExtra("Introvert",0)
        val extrovert = intent.getIntExtra("extrovert",0)
        val mydate = intent.getStringExtra("date")

        if(introvert > extrovert){
            MytextView.text = "Twoja osobowosc to introwertyk"
            MyImage.setImageResource(R.drawable.introvert)
        }
        else if (extrovert > introvert){
            MytextView.text = "Twoja osobowosc to extrawertyk"
            MyImage.setImageResource(R.drawable.extrovert)
        }
        else{
            MytextView.text = "Twoja osobowosc to Ambiwertyk"
            MyImage.setImageResource(R.drawable.ambivert)
        }

        dateTextView.text = "rozwiazywales quiz dnia $mydate"





    }
}
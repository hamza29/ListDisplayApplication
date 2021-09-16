package com.example.listdisplayapplication

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DetailActivity : AppCompatActivity() {
    var titleTextview: TextView? = null
    var descTextView: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        titleTextview = findViewById<TextView>(R.id.title)
        descTextView = findViewById<TextView>(R.id.description)
        if (intent != null) {
            titleTextview!!.setText(intent.getStringExtra("title"))
            descTextView!!.setText(intent.getStringExtra("desc"))
        }

    }
}
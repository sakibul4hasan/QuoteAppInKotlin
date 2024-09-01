package com.example.quotify

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var quoteText: TextView
    private lateinit var quoteAuthor: TextView
    private lateinit var shareBtn: FloatingActionButton
    private lateinit var previousBtn: Button
    private lateinit var nextBtn: Button
    lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        quoteText = findViewById(R.id.quoteText)
        quoteAuthor = findViewById(R.id.quoteAuthor)
        shareBtn = findViewById(R.id.shareBtn)
        previousBtn = findViewById(R.id.previousBtn)
        nextBtn = findViewById(R.id.nextBtn)

        mainViewModel = ViewModelProvider(this, MainViewModelFactory(application)).get(MainViewModel::class.java)
        setQuote(mainViewModel.getQuote())

        nextBtn.setOnClickListener {
            setQuote(mainViewModel.nextQuote())
        }

        previousBtn.setOnClickListener {
            setQuote(mainViewModel.previousQuote())
        }

        shareBtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.setType("text/plain")
            intent.putExtra(Intent.EXTRA_TEXT, mainViewModel.getQuote().quote)
            startActivity(intent)
        }
    }

    fun setQuote(quote: Quote) {
        quoteText.text = quote.quote
        quoteAuthor.text = quote.author
    }
}
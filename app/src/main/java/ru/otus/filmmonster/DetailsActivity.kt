package ru.otus.filmmonster

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import java.lang.NullPointerException

class Details_Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.details)
        try {
            val film: Film = intent.getParcelableExtra<Film>("film")!!
            Log.d("__OTUS__","intent OK")
            findViewById<ImageView>(R.id.det_poster).setImageResource(film.poster)
        } catch (e: NullPointerException){
            Log.d("__OTUS__","NullPointerException")
            Toast.makeText(this, "no such film", Toast.LENGTH_SHORT).show()
        }
    }
}
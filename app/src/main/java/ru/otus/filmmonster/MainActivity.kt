package ru.otus.filmmonster

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        films = createFilms()

        selectFilm(savedInstanceState?.getInt("select", -1) ?:-1)

        findViewById<View>(R.id.button_details1).setOnClickListener(detailsOnClickListener)
        findViewById<View>(R.id.button_details2).setOnClickListener(detailsOnClickListener)
        findViewById<View>(R.id.button_details3).setOnClickListener(detailsOnClickListener)
    }

    override fun onSaveInstanceState(outState: Bundle){
        super.onSaveInstanceState(outState)
        outState.putInt("select", selected)
    }


    var detailsOnClickListener = View.OnClickListener {
        when (it.id){
            R.id.button_details1 -> {
                selected = 0
                selectFilm(selected)
                openDetails(0)
            }
            R.id.button_details2 -> {
                selected = 1
                selectFilm(selected)
                openDetails(1)
            }
            R.id.button_details3 -> {
                selected = 2
                selectFilm(selected)
                openDetails(2)
            }
        }
    }

    fun openDetails(id: Int){
        val intentDetails = Intent(this, Details_Activity::class.java)
        intentDetails.putExtra("film",films[id])
        Log.d("__OTUS__","putExtra")
        startActivity(intentDetails, Bundle())
    }

    var selected: Int = -1
    lateinit var films: List<Film>

    fun createFilms(): List<Film> {
        val film1 = Film(
            1,
            R.string.film1,
            R.drawable.norway,
            R.string.description1,
        )
        val film2 = Film(
            2,
            R.string.film2,
            R.drawable.the_wire,
            R.string.description2,
        )
        val film3 = Film(
            3,
            R.string.film1,
            R.drawable.true_detective,
            R.string.description1,
        )
        val films = listOf(film1, film2, film3)
        return films
    }


    fun selectFilm(selected: Int){
        unSelectFilms()
        when (selected){
            0 -> {findViewById<TextView>(R.id.film_name1).setTextColor(Color.RED)}
            1 -> {findViewById<TextView>(R.id.film_name2).setTextColor(Color.RED)}
            2 -> {findViewById<TextView>(R.id.film_name3).setTextColor(Color.RED)}
        }
    }

    fun unSelectFilms(){
        findViewById<TextView>(R.id.film_name1).setTextColor(Color.BLACK)
        findViewById<TextView>(R.id.film_name2).setTextColor(Color.BLACK)
        findViewById<TextView>(R.id.film_name3).setTextColor(Color.BLACK)
    }
}
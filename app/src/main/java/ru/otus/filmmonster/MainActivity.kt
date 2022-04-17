package ru.otus.filmmonster

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        get_films()

        findViewById<View>(R.id.button_details1).setOnClickListener(detailsOnClickListener)
        findViewById<View>(R.id.button_details2).setOnClickListener(detailsOnClickListener)
        findViewById<View>(R.id.button_details3).setOnClickListener(detailsOnClickListener)
    }

    var detailsOnClickListener = View.OnClickListener {
        when (it.id){
            R.id.button_details1 -> open_details(0)
            R.id.button_details2 -> open_details(1)
            R.id.button_details3 -> open_details(2)
        }
    }

    fun open_details(id: Int){

    }

    fun get_films(){
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

        var films = listOf(film1, film2, film3)
    }
}
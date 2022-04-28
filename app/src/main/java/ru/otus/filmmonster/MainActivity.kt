package ru.otus.filmmonster

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import android.os.Bundle as AndroidOsBundle
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private val recyclerView by lazy{findViewById<RecyclerView>(R.id.recycler)}

    override fun onCreate(savedInstanceState: AndroidOsBundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        films = createFilms()

                selectFilm(savedInstanceState?.getInt("select", -1) ?:-1)

        //findViewById<View>(R.id.button_details).setOnClickListener(detailsOnClickListener)
        //findViewById<View>(R.id.button_details2).setOnClickListener(detailsOnClickListener)
        //findViewById<View>(R.id.button_details3).setOnClickListener(detailsOnClickListener)
        initRecycler()
    }

    private fun initRecycler(){
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = FilmItemAdapter(films, detailsOnClickListener)
    }

    override fun onBackPressed() {
        onCreateDialog()
    }

    private fun onCreateDialog() {
        return AlertDialog.Builder(this)
            .setTitle(R.string.exit)
            .setMessage(getString(R.string.exit_question))
            .setNegativeButton(R.string.no) { dialog, which -> }
            .setPositiveButton(R.string.yes) { dialog, which -> finish()}
            .create()
            .show()
    }

    override fun onSaveInstanceState(outState: AndroidOsBundle){
        super.onSaveInstanceState(outState)
        outState.putInt("select", selected)
    }

    var detailsOnClickListener = View.OnClickListener {
        when (it.id){
            R.id.button_details -> {
                selected = 0
                selectFilm(selected)
                openDetails(0)
            }
            //R.id.button_details2 -> {
            //    selected = 1
            //    selectFilm(selected)
            //    openDetails(1)
            //}
           // R.id.button_details3 -> {
            //    selected = 2
            //    selectFilm(selected)
             //   openDetails(2)
            }
    }


    fun openDetails(id: Int){
        val intentDetails = Intent(this, DetailsActivity::class.java)
        intentDetails.putExtra(EXTRA_FILM,films[id])
        //Log.d("_OTUS_","putExtra")
        //startActivity(intentDetails, Bundle())
        getFilm.launch(intentDetails)
    }

    var selected: Int = -1
    lateinit var films: MutableList<Film>

    fun createFilms(): MutableList<Film> {
         films = mutableListOf(
          Film(
            0,
            R.string.film1,
            R.drawable.norway,
            R.string.description1,
        ),
         Film(
            1,
            R.string.film2,
            R.drawable.the_wire,
            R.string.description2,
        ),
        Film(
            2,
            R.string.film3,
            R.drawable.true_detective,
            R.string.description3,
        ),
         Film(
             3,
             R.string.film1,
             R.drawable.norway,
             R.string.description1,
         ),
         Film(
             4,
             R.string.film2,
             R.drawable.the_wire,
             R.string.description2,
         ),
         Film(
             5,
             R.string.film3,
             R.drawable.true_detective,
             R.string.description3,
         ),
         Film(
             6,
             R.string.film1,
             R.drawable.norway,
             R.string.description1,
         ),
         Film(
             7,
             R.string.film2,
             R.drawable.the_wire,
             R.string.description2,
         ),
         Film(
             8,
             R.string.film3,
             R.drawable.true_detective,
             R.string.description3,
         ),
         Film(
             9,
             R.string.film1,
             R.drawable.norway,
             R.string.description1,
         ),
         Film(
             10,
             R.string.film2,
             R.drawable.the_wire,
             R.string.description2,
         ),
         Film(
             11,
             R.string.film3,
             R.drawable.true_detective,
             R.string.description3,
         ),
         Film(
             12,
             R.string.film1,
             R.drawable.norway,
             R.string.description1,
         ),
         Film(
             13,
             R.string.film2,
             R.drawable.the_wire,
             R.string.description2,
         ),
         Film(
             14,
             R.string.film3,
             R.drawable.true_detective,
             R.string.description3,
         ),
         Film(
             15,
             R.string.film1,
             R.drawable.norway,
             R.string.description1,
         ),
         Film(
             16,
             R.string.film2,
             R.drawable.the_wire,
             R.string.description2,
         ),
         Film(
             17,
             R.string.film3,
             R.drawable.true_detective,
             R.string.description3,
         ),
         Film(
             18,
             R.string.film1,
             R.drawable.norway,
             R.string.description1,
         ),
         Film(
             19,
             R.string.film2,
             R.drawable.the_wire,
             R.string.description2,
         ),
         Film(
             20,
             R.string.film3,
             R.drawable.true_detective,
             R.string.description3,
         ),
         Film(
             21,
             R.string.film1,
             R.drawable.norway,
             R.string.description1,
         ),
         Film(
             22,
             R.string.film2,
             R.drawable.the_wire,
             R.string.description2,
         ),
         Film(
             23,
             R.string.film3,
             R.drawable.true_detective,
             R.string.description3,
         ),

        )
        //val films = mutableListOf<Film>(film1, film2, film3)
        return films
    }


    fun selectFilm(selected: Int){
        unSelectFilms()
        when (selected){
            //0 -> {findViewById<TextView>(R.id.film_name).setTextColor(Color.RED)}
            //1 -> {findViewById<TextView>(R.id.film_name2).setTextColor(Color.RED)}
            //2 -> {findViewById<TextView>(R.id.film_name3).setTextColor(Color.RED)}
        }
    }

    fun unSelectFilms(){
        //findViewById<TextView>(R.id.film_name).setTextColor(Color.BLACK)
        //findViewById<TextView>(R.id.film_name).setTextColor(Color.BLACK)
        //findViewById<TextView>(R.id.film_name).setTextColor(Color.BLACK)
    }

    val getFilm = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        result ->
            val data = result.data
            if (result.resultCode == RESULT_OK && data != null){
                val film: Film? = data.getParcelableExtra(DetailsActivity.EXTRA_FILM)
                film?.let{
                    val id = it.id
                    films[id] = it
                    Log.d("_OTUS_", it.comment)
                    Log.d("_OTUS_","${it.like}")
                }
            }
    }

    companion object {
        const val EXTRA_FILM = "film"
    }
}
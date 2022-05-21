package ru.otus.filmmonster

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity



class DetailsActivity : AppCompatActivity() {

    var film: Film? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.details)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getText(R.string.details)
        try {
            val filmLocal = intent?.getParcelableExtra<Film>("film") ?: throw IllegalStateException("No film data provided")
            //Log.d("_OTUS_","intent OK")
            /*findViewById<ImageView>(R.id.det_poster).setImageResource(filmLocal.poster)*/
            findViewById<TextView>(R.id.det_film_name).setText(filmLocal.name)
            findViewById<TextView>(R.id.det_description).setText(filmLocal.description)
            findViewById<EditText>(R.id.det_comment).setText(filmLocal.comment)
            findViewById<CheckBox>(R.id.det_like).isChecked = filmLocal.like

            findViewById<Button>(R.id.det_invite).setOnClickListener(){
                val intentShare = Intent(Intent.ACTION_SEND)
                intentShare.putExtra(Intent.EXTRA_TEXT, "Советую посмотреть фильм ${getString(filmLocal.name)}")
                intentShare.type = "text/plain"
                startActivity(Intent.createChooser(intentShare, "Поделиться"))
            }
            film = filmLocal
        } catch (e: IllegalStateException){
            Log.d("_OTUS_","NullPointerException")
            Toast.makeText(this, "no such film", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
    override fun onBackPressed() {
        film?.comment = findViewById<EditText>(R.id.det_comment).text.toString()
        film?.like = findViewById<CheckBox>(R.id.det_like).isChecked
        val savedFilm = Intent()
        savedFilm.putExtra(EXTRA_FILM, film)
        setResult(RESULT_OK, savedFilm)
        //Log.d("_OTUS_","${film?.comment}")
        //Log.d("_OTUS_","${film?.like}")
        //Log.d("_OTUS_","film saved")
        super.onBackPressed()
    }

    companion object {
        const val EXTRA_FILM = "film"
    }
}

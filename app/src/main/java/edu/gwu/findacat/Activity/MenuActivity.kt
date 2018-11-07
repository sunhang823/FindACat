package edu.gwu.findacat.Activity


import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import edu.gwu.findacat.CatFactFetcher
import edu.gwu.findacat.R
import kotlinx.android.synthetic.main.activity_menu.*
import org.jetbrains.anko.toast



import android.content.res.Configuration;
import java.util.Locale;


class MenuActivity : AppCompatActivity(){
    private val LOCATION_PERMISSION_REQUEST_CODE = 7
    private lateinit var catFactFetcher: CatFactFetcher
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)




        setContentView(R.layout.activity_menu)
        find_a_cat_button.setOnClickListener{
            val intent = Intent(this, PetsActivity::class.java)
            startActivity(intent)
        }
        favorite_cats_button.setOnClickListener{
            val intent = Intent(this, FavoritePetsActivity::class.java)
            startActivity(intent)
        }
        getCatFact()
        requestPermissionsIfNecessary()
    }
    private fun requestPermissionsIfNecessary() {
        val permission = ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
        if(permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if(requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if(grantResults.isNotEmpty() && grantResults.first() == PackageManager.PERMISSION_GRANTED) {
                toast(R.string.permissions_granted)
            }
            else {
                toast(R.string.permissions_denied)
            }
        }
    }

    fun getCatFact() {
        catFactFetcher= CatFactFetcher(cat_fact_textView)
        catFactFetcher.searchCatFact()
    }
}

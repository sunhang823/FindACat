package edu.gwu.findacat.Activity

import java.util.Locale;
import android.content.Intent
import android.app.AlertDialog;
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import edu.gwu.findacat.generated.PetFinder.PetItem
import kotlinx.android.synthetic.main.activity_pets.*
import android.widget.EditText
import android.view.LayoutInflater
import edu.gwu.findacat.*
import edu.gwu.findacat.R.string.zip
import org.jetbrains.anko.toast
import java.util.*


class PetsActivity : AppCompatActivity(), PetFinder.PetSearchCompletionListener,PetsAdapter.onPetClickListener,LocationDetector.LocationListener {
    private lateinit var petFinder: PetFinder
    private lateinit var locationDetector: LocationDetector
    companion object {
        const val PET_INDEX = "petIndex"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pets)
        setSupportActionBar(pets_toolbar)

        locationDetector = LocationDetector(this)
        locationDetector.locationListener = this
        locationDetector.detectLocation()
    }
    override fun locationFound(location: Location){
        val zipCode = getZipCodeFromLocation(location)
        toast("${zipCode}")
        petFinder = PetFinder()
        petFinder.petSearchCompletionListener = this
        petFinder.searchPets(zipCode)

    }

    override fun locationNotFound(reason: LocationDetector.FailureReason) {
        when (reason) {
            LocationDetector.FailureReason.TIMEOUT -> toast(getString(R.string.location_not_found))
            LocationDetector.FailureReason.NO_PERMISSION -> toast(getString(R.string.no_location_permission))
        }

        petFinder = PetFinder()
        petFinder.petSearchCompletionListener = this
        petFinder.searchPets(Constants.DEFAULT_PET_Finder_Location)


    }

    private fun getZipCodeFromLocation(location: Location):String{
        val geocoder = Geocoder(this, Locale.getDefault())
        val address = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1)
        val zipCode = address[0].postalCode
        return zipCode
    }

    private fun displayPetInformation(petItems: List<PetItem>){
        cats_recycler_view.layoutManager = LinearLayoutManager(this)
        cats_recycler_view.adapter = PetsAdapter(petItems,this)
    }
    override fun petsLoaded(petItems: List<PetItem>) {
        displayPetInformation(petItems)
    }
    override fun petsNotLoaded() {
        finish()
    }
    override fun onPetClick(petItem: PetItem, petView: View) {
        val intent = Intent(this, PetsDetailsActivity::class.java)
        intent.putExtra(PET_INDEX, petItem)
        startActivity(intent)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_pets, menu)
        return super.onCreateOptionsMenu(menu)
    }
    fun zipCodeButtonPressed(item: MenuItem) {
        showInputDialog()
    }
    private fun showInputDialog() {
        val layoutInflater = LayoutInflater.from(this)
        val promptView = layoutInflater.inflate(R.layout.zip_code_dialog, null)
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setView(promptView)

        val editText = promptView.findViewById<View>(R.id.zip_code_edit_text) as EditText

        alertDialogBuilder.setCancelable(false)
                .setPositiveButton(R.string.ok,{dialog,id -> petFinder.searchPets(editText.text.toString()) })
                .setNegativeButton(R.string.cancel, {dialog,id -> dialog.cancel()})
        val alert = alertDialogBuilder.create()
        alert.show()
    }
}

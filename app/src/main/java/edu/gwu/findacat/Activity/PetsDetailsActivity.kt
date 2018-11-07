package edu.gwu.findacat.Activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.squareup.picasso.Picasso
import edu.gwu.findacat.PersistenceManager
import edu.gwu.findacat.R
import edu.gwu.findacat.generated.PetFinder.PetItem
import kotlinx.android.synthetic.main.activity_pets_details.*

class PetsDetailsActivity : AppCompatActivity() {

    private lateinit var petItem:PetItem
    private lateinit var persistenceManager: PersistenceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pets_details)
        setSupportActionBar(pets_detail_toolbar)

        petItem = intent.getParcelableExtra(PetsActivity.PET_INDEX)
        displayPetDetail(petItem)
        persistenceManager = PersistenceManager(this)
    }
    private fun displayPetDetail(petItem:PetItem){
        cat_details_name_text_view.text=("${resources.getString(R.string.name)} ${petItem.name?.T}")
        cat_details_gender_text_view.text=("${resources.getString(R.string.gender)} ${petItem.sex?.T}")
        cat_details_zip_text_view.text=("${resources.getString(R.string.zip)} ${petItem.contact?.zip?.T}")
        cat_details_email_text_view.text=("${resources.getString(R.string.emailColon)} ${petItem.contact?.email?.T}")

        cat_details_description_text_view.text=("${resources.getString(R.string.description)} ${petItem.description?.T}")
        petItem.media?.photos?.photo?.let {
            var uri = Uri.parse(it[1]?.T)
            Picasso.get().load(uri).into(cat_details_image_view, object: com.squareup.picasso.Callback {
                override fun onSuccess() {
                }
                override fun onError(e: java.lang.Exception?) {
                }
            })
        }
    }


    // toolbar
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_pets_detail, menu)
        return super.onCreateOptionsMenu(menu)
    }
    fun shareButtonPressed(item: MenuItem) {
        val shareIntent = Intent()
        shareIntent.action = Intent.ACTION_SEND
        val shareText = "This cat is adorable. Name: ${petItem.name?.T}. Email: ${petItem.contact?.email?.T} "
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareText)
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_STREAM, petItem.media?.photos?.photo?.get(1)?.T)
        shareIntent.type = "image/*"
        startActivity(Intent.createChooser(shareIntent,"Share a pet to..."))
    }

    fun emailButtonPressed(item: MenuItem){
        val emailIntent = Intent()
        emailIntent.action = Intent.ACTION_SENDTO
        val email = petItem.contact?.email?.T
        val subject = "Interested in a pet"
        val body = "Dear owner: I really like ${petItem.name?.T}. Please contact me! Thank you!"
        val uriText = "mailto:" + Uri.encode(email) + "?subject=" + Uri.encode(subject) + "&body=" + Uri.encode(body)
        val uri = Uri.parse(uriText)
        emailIntent.data = uri
        startActivity(Intent.createChooser(emailIntent, "Choose an Email client :"))

    }

    fun likeButtonPressed(item: MenuItem){

        val petItems = persistenceManager.fetchPetItems().toMutableList()
        if(petItems.contains(petItem)){
            persistenceManager.deletePetItem(petItem)

        }else{
            persistenceManager.savePetItem(petItem)
        }
    }
}

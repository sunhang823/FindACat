package edu.gwu.findacat.Activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import edu.gwu.findacat.PersistenceManager
import edu.gwu.findacat.PetsAdapter
import edu.gwu.findacat.R
import edu.gwu.findacat.generated.PetFinder.PetItem
import kotlinx.android.synthetic.main.activity_pets.*

class FavoritePetsActivity : AppCompatActivity(),PetsAdapter.onPetClickListener {

    private lateinit var persistenceManager: PersistenceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite_pets)

        persistenceManager = PersistenceManager(this)

        val petItems = persistenceManager.fetchPetItems()
        cats_recycler_view.layoutManager = LinearLayoutManager(this)
        cats_recycler_view.adapter = PetsAdapter(petItems,this)
    }

    override fun onPetClick(petItem: PetItem, petView: View) {
        val intent = Intent(this, PetsDetailsActivity::class.java)
        intent.putExtra(PetsActivity.PET_INDEX, petItem)
        startActivity(intent)
    }
}

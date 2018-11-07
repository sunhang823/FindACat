package edu.gwu.findacat

import android.content.ContentValues
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.util.Log
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import edu.gwu.findacat.generated.PetFinder.PetItem
import java.io.IOException
import java.util.*

class PersistenceManager(private val context: Context) {
    private val sharedPreferences: SharedPreferences

    init {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
    }
    fun savePetItem(petItem: PetItem){
        val petItems = fetchPetItems().toMutableList()
        petItems.add(petItem)

        val editor = sharedPreferences.edit()

        //convert a list of petItems into a JSON string
        val moshi = Moshi.Builder().add(Date::class.java, Rfc3339DateJsonAdapter()).build()
        val listType = Types.newParameterizedType(List::class.java, PetItem::class.java)
        val jsonAdapter = moshi.adapter<List<PetItem>>(listType)
        val jsonString = jsonAdapter.toJson(petItems)

        editor.putString(Constants.PETITEMS_PREF_KEY, jsonString)

        editor.apply()
    }

    fun deletePetItem(petItem: PetItem){
        val petItems = fetchPetItems().toMutableList()
        petItems.remove(petItem)

        val editor = sharedPreferences.edit()

        //convert a list of petItems into a JSON string
        val moshi = Moshi.Builder().add(Date::class.java, Rfc3339DateJsonAdapter()).build()
        val listType = Types.newParameterizedType(List::class.java, PetItem::class.java)
        val jsonAdapter = moshi.adapter<List<PetItem>>(listType)
        val jsonString = jsonAdapter.toJson(petItems)

        editor.putString(Constants.PETITEMS_PREF_KEY, jsonString)

        editor.apply()

    }


    fun fetchPetItems():List<PetItem>{
        val jsonString = sharedPreferences.getString(Constants.PETITEMS_PREF_KEY, null)
        if(jsonString == null) {
            return arrayListOf<PetItem>()
        }else {
            //existing petItems, so convert the petItems JSON string into PetItem objects, using Moshi
            val listType = Types.newParameterizedType(List::class.java, PetItem::class.java)
            val moshi = Moshi.Builder()
                    .add(Date::class.java, Rfc3339DateJsonAdapter())
                    .build()
            val jsonAdapter = moshi.adapter<List<PetItem>>(listType)

            var petItems:List<PetItem>? = emptyList<PetItem>()
            try {
                petItems = jsonAdapter.fromJson(jsonString)
            } catch (e: IOException) {
                Log.e(ContentValues.TAG, e.message)
            }

            if(petItems != null) {
                return petItems
            }
            else {
                return emptyList<PetItem>()
            }
        }
    }

}
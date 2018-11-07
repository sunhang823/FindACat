package edu.gwu.findacat

import android.util.Log
import edu.gwu.findacat.generated.PetFinder.PetFinderResponse
import edu.gwu.findacat.generated.PetFinder.PetItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query





class PetFinder{
    private val TAG = "PetSearchManager"
    var petSearchCompletionListener: PetSearchCompletionListener? = null

    interface PetSearchCompletionListener {
        fun petsLoaded(petItems: List<PetItem>)
        fun petsNotLoaded()
    }

    interface ApiEndpointInterface {
        @GET("pet.find")
        fun findPets(@Query("key") key: String, @Query("format") format: String, @Query("animal") animal: String, @Query("location") location: String): Call<PetFinderResponse>
    }

    fun searchPets(location: String) {
        val retrofit = Retrofit.Builder()
                .baseUrl(Constants.PET_FINDER_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()

        val apiEndpoint = retrofit.create(ApiEndpointInterface::class.java)

        apiEndpoint.findPets(Constants.PET_FINDER_API_KEY, Constants.PET_FINDER_FORMAT, Constants.PET_FINDER_ANIMAL, location).enqueue(object: Callback<PetFinderResponse> {
            override fun onFailure(call: Call<PetFinderResponse>, t: Throwable) {
                Log.d(TAG, "API call failed!")
                petSearchCompletionListener?.petsNotLoaded()
            }

            override fun onResponse(call: Call<PetFinderResponse>, response: Response<PetFinderResponse>) {
                val petsResponseBody = response.body()

                petsResponseBody?.petfinder?.pets?.pet?.let {
                    petSearchCompletionListener?.petsLoaded(it)
                    return
                }

                petSearchCompletionListener?.petsNotLoaded()
            }
        })
    }
}



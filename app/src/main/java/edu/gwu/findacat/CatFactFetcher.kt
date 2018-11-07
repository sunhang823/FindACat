package edu.gwu.findacat

import android.content.Context
import android.util.Log
import android.widget.TextView
import edu.gwu.findacat.generated.CatFact.CatFactResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET


class CatFactFetcher(private val catFactTextView: TextView) {
    private val TAG = "CatFactFetcher"
    interface CatFactsApiEndpointInterface {
        @GET("fact")
        fun getCatFactResponse(): Call<CatFactResponse>
    }

    fun searchCatFact() {
        val retrofit = Retrofit.Builder()
                .baseUrl(Constants.CAT_FACT_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()

        val apiEndpoint = retrofit.create(CatFactsApiEndpointInterface::class.java)

        apiEndpoint.getCatFactResponse().enqueue(object: Callback<CatFactResponse> {
            override fun onFailure(call: Call<CatFactResponse>, t: Throwable) {
                Log.d(TAG, "API call failed!")
                catFactTextView.text ="null"
            }

            override fun onResponse(call: Call<CatFactResponse>, response: Response<CatFactResponse>) {
                val catFactResponseBody = response.body()
                if(catFactResponseBody != null) {
                    catFactTextView.text = catFactResponseBody.fact
                } else {
                    catFactTextView.text ="null"
                }
            }
        })
    }
}

package edu.gwu.findacat.generated.CatFact

import com.squareup.moshi.Json

data class CatFactResponse(

        @field:Json(name="fact")
        val fact: String? = null,

        @field:Json(name="length")
        val length:Int? = null

)
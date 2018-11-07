package edu.gwu.findacat.generated.PetFinder

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PetFinderResponse(

	@field:Json(name="petfinder")
	val petfinder: Petfinder? = null,

	@field:Json(name="@version")
	val version: String? = null,

	@field:Json(name="@encoding")
	val encoding: String? = null
): Parcelable
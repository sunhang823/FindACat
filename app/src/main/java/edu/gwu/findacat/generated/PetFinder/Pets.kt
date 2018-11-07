package edu.gwu.findacat.generated.PetFinder


import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize


data class Pets(

	@field:Json(name="pet")
//	val pet: List<PetItem?>? = null
	val pet: List<PetItem>? = null
):Parcelable
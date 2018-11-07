package edu.gwu.findacat.generated.PetFinder


import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Photos(

	@field:Json(name="photo")
	val photo: List<PhotoItem?>? = null
):Parcelable
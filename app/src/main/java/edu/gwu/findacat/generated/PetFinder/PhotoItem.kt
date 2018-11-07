package edu.gwu.findacat.generated.PetFinder


import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PhotoItem(

	@field:Json(name="\$t")
	val T: String? = null,

	@field:Json(name="@size")
	val size: String? = null,

	@field:Json(name="@id")
	val id: String? = null
):Parcelable
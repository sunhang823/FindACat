package edu.gwu.findacat.generated.PetFinder


import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize


data class Header(

	@field:Json(name="version")
	val version: Version? = null,

	@field:Json(name="timestamp")
	val timestamp: Timestamp? = null,

	@field:Json(name="status")
	val status: Status? = null
):Parcelable
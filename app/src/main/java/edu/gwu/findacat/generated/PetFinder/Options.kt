package edu.gwu.findacat.generated.PetFinder


import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Options(

	@field:Json(name="option")
	val option: List<OptionItem?>? = null
):Parcelable
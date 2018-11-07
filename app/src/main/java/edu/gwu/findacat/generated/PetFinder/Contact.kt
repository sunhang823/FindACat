package edu.gwu.findacat.generated.PetFinder


import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Contact(

	@field:Json(name="zip")
	val zip: Zip? = null,

	@field:Json(name="phone")
	val phone: Phone? = null,

//	@field:Json(name="address2")
//	val address2: Address2? = null,

	@field:Json(name="city")
	val city: City? = null,

	@field:Json(name="address1")
	val address1: Address1? = null,

	@field:Json(name="state")
	val state: State? = null,

//	@field:Json(name="fax")
//	val fax: Fax? = null,

	@field:Json(name="email")
	val email: Email? = null
):Parcelable
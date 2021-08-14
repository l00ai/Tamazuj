package com.corsuevisionplus.tamazuj.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import javax.annotation.Generated


class UserInfo {
    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("email")
    @Expose
    var email: String? = null

    @SerializedName("profile_photo")
    @Expose
    var profilePhoto: String? = null

    @SerializedName("gender")
    @Expose
    var gender: String? = null

    @SerializedName("lang")
    @Expose
    var lang: String? = null

    @SerializedName("phone")
    @Expose
    var phone: String? = null

    @SerializedName("date_of_berth")
    @Expose
    var dateOfBerth: String? = null


}

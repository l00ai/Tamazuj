package com.corsuevisionplus.tamazuj.api.response

import com.corsuevisionplus.tamazuj.models.UserInfo

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName
import javax.annotation.Generated

class SettingsResponse {
    @SerializedName("status")
    @Expose
    var status: Boolean? = null

    @SerializedName("user_info")
    @Expose
    var userInfo: UserInfo? = null

    @SerializedName("message")
    @Expose
    var message: String? = null
}
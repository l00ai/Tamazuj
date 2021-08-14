package com.corsuevisionplus.tamazuj.api.response

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName
import javax.annotation.Generated

class RestResponse {
    @SerializedName("status")
    @Expose
    var status: Boolean? = null

    @SerializedName("message")
    @Expose
    var message: String? = null
}
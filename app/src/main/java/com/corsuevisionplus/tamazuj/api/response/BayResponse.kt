package com.corsuevisionplus.tamazuj.api.response

import com.corsuevisionplus.tamazuj.models.OnlineDataBay
import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class BayResponse {
    @SerializedName("states")
    @Expose
    var states: Boolean? = null

    @SerializedName("bay_link")
    @Expose
    var bayLink: String? = null

    @SerializedName("online_data")
    @Expose
    var onlineDataBay: OnlineDataBay? = null
}
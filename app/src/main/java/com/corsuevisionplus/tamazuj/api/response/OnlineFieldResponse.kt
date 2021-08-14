package com.corsuevisionplus.tamazuj.api.response

import com.corsuevisionplus.tamazuj.models.DataOnline
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class OnlineFieldResponse {
    @SerializedName("status")
    @Expose
    var status: Boolean? = null

    @SerializedName("data")
    @Expose
    var data: List<DataOnline>? = null
}

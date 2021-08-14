package com.corsuevisionplus.tamazuj.api.response

import com.corsuevisionplus.tamazuj.models.DataTime

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class TimeResponse {
    @SerializedName("status")
    @Expose
    var status: Boolean? = null

    @SerializedName("data")
    @Expose
    var dataTime: List<DataTime>? = null
}
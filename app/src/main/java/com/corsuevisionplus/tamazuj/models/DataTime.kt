package com.corsuevisionplus.tamazuj.models

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class DataTime {
    @SerializedName("open_type")
    @Expose
    var openType: Boolean? = null

    @SerializedName("date_to")
    @Expose
    var dateTo: String? = null

    @SerializedName("date_from")
    @Expose
    var dateFrom: String? = null

    @SerializedName("time_start")
    @Expose
    var timeStart: String? = null

    @SerializedName("time_end")
    @Expose
    var timeEnd: String? = null
}
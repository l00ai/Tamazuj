package com.corsuevisionplus.tamazuj.api.response

import com.corsuevisionplus.tamazuj.models.DataOutline

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName






class OutlineFieldResponse {
    @SerializedName("states")
    @Expose
    var states: Boolean? = null

    @SerializedName("data")
    @Expose
    var data: List<DataOutline>? = null
}
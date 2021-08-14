package com.corsuevisionplus.tamazuj.models

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class DataOnline {
    @SerializedName("doctor_id")
    @Expose
    var doctorId: Int? = null

    @SerializedName("doctor_name")
    @Expose
    var doctorName: String? = null

    @SerializedName("fields")
    @Expose
    var fields: List<Field>? = null
}

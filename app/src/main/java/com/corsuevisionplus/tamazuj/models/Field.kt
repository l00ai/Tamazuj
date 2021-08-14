package com.corsuevisionplus.tamazuj.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Field {
    @SerializedName("field_id")
    @Expose
    var fieldId: Int? = null

    @SerializedName("field_name")
    @Expose
    var fieldName: String? = null

    @SerializedName("price")
    @Expose
    var price: Double? = null
}

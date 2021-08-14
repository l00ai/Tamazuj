package com.corsuevisionplus.tamazuj.models

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class OnlineDataBay {
    @SerializedName("remaining_online_seconds")
    @Expose
    var remainingOnlineSeconds: Int? = null

    @SerializedName("consultation_id")
    @Expose
    var consultationId: Int? = null

    @SerializedName("firestore_collection_id")
    @Expose
    var firestoreCollectionId: String? = null
}

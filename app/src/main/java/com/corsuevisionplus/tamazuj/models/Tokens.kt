package com.corsuevisionplus.tamazuj.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import javax.annotation.Generated


class Tokens {
    @SerializedName("access_token")
    @Expose
    var accessToken: String? = null

    @SerializedName("fcm_token")
    @Expose
    var fcmToken: String? = null
}

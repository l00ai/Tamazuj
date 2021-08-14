package com.corsuevisionplus.tamazuj.api.response

import com.corsuevisionplus.tamazuj.models.Tokens
import com.corsuevisionplus.tamazuj.models.UserInfo
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import javax.annotation.Generated

class CommonResponse {

    @SerializedName("status")
    @Expose
    var status: Boolean? = null
    @SerializedName("user_info")
    @Expose
    var userInfo: UserInfo? = null

    @SerializedName("tokens")
    @Expose
    var tokens: Tokens? = null
}
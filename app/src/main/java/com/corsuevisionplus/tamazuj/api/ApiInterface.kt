package com.corsuevisionplus.tamazuj.api

import com.corsuevisionplus.tamazuj.api.response.*
import retrofit2.Call
import retrofit2.http.*


interface ApiInterface {
    // User Interface
    @FormUrlEncoded
    @POST("api-v1/auth/register")
    fun register(@Field("name") name: String,
                 @Field("email") email: String,
                 @Field("phone") phone: String,
                 @Field("lang") language: String,
                 @Field("gender") gender: String,
                 @Field("date_of_berth") dateOfBerth: String,
                 @Field("fcm_token") fcm_token: String,
                 @Field("password") password: String,
                 @Field("password_confirmation") cpassword: String
    ): Call<CommonResponse>


    @FormUrlEncoded
    @POST("api-v1/auth/login")
    fun logIn(@Field("email") email: String,
              @Field("password") password: String,
              @Field("fcm_token") fcm_token: String
    ): Call<CommonResponse>

    @FormUrlEncoded
    @POST("api-v1/customer-reset-password/send-reset-password")
    fun rest(@Field("email") email: String
    ): Call<RestResponse>

    @FormUrlEncoded
    @POST("api-v1/customer-settings/update")
    fun upDate(@Field("name") name: String,
               @Field("email") email: String,
               @Field("phone") phone: String,
               @Field("lang") language: String,
               @Field("gender") gender: String,
               @Field("date_of_berth") dateOfBerth: String,
               @Field("password") password: String
    ): Call<SettingsResponse>

   // @FormUrlEncoded
    @POST("api-v1/auth/logout")
    @Headers("Content-Type: application/json")
    fun logOutApp(): Call<LogOutResponse>

    //OutLine Interface



    @GET("api-v1/room-reservations/get-doctors-failed")
    fun getFields(): Call<OutlineFieldResponse>

    @GET("api-v1/room-reservations/get-doctors-failed")
    fun getDoctors(): Call<OutlineFieldResponse>

    @FormUrlEncoded
    @POST("api-v1/room-reservations/get-available-times")
    fun availableTime(@Field("date") date: String
    ): Call<TimeResponse>

    @FormUrlEncoded
    @POST("api-v1/room-reservations/bay-new-room-consulting")
    fun roomOutline(@Field("doctor_id") doctor_id: String,
                   @Field("field_id") field_id: String,
                   @Field("note") note: String,
                    @Field("payment_type") payment_type: String,
                    @Field("room_consultant_at_from") room_consultant_at_from: String,
                   @Field("room_consultant_at_to") room_consultant_at_to: String,
    ): Call<BayResponse>




// Online Interface

    @GET("api-v1/online-reservations/get-online-doctors")
    fun getFieldsOnline(): Call<OnlineFieldResponse>


    @GET("api-v1/online-reservations/get-online-doctors")
    fun getDoctorsOnline(): Call<OnlineFieldResponse>


    @FormUrlEncoded
    @Headers("Content-Type: application/json")
    @POST("api-v1/online-reservations/bay-new-online-consulting")
    fun roomOnline(@Field("doctor_id") doctor_id: String,
                   @Field("field_id") field_id: String,
                   @Field("note") note: String,
                   @Field("payment_type") payment_type: String,
                   @Field("minutes") minutes: String
    ): Call<BayResponse>




}


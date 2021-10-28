package com.example.centro_oculistico.adapter

import com.google.gson.annotations.SerializedName

data class TimeSlot(
    @SerializedName("doctor_id") var doctor_id: String?,
    @SerializedName("passwd") val passwd: String?,
    @SerializedName("slots") val slots: Array<String>?
    )

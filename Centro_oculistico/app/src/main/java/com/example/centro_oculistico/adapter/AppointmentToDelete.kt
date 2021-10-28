package com.example.centro_oculistico.adapter

import com.google.gson.annotations.SerializedName

data class AppointmentToDelete(

    @SerializedName("cdf") val cdf: String,
    @SerializedName("appointmentId") val appointmentId : String
)

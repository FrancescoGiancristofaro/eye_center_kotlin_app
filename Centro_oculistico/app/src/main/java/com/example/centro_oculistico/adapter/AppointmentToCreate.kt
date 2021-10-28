package com.example.centro_oculistico.adapter

import com.google.gson.annotations.SerializedName

data class AppointmentToCreate (
    @SerializedName("cdf") val cdf: String?,
    @SerializedName("doctorId") val doctorId: String?,
    @SerializedName("timeSlot") val timeSlot: String?,
    @SerializedName("branchId") val branchId: String?,
    @SerializedName("day") val day: String?
    )
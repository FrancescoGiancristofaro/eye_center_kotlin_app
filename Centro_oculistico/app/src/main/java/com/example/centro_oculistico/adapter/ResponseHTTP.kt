package com.example.centro_oculistico.adapter

import com.google.gson.annotations.SerializedName

data class ResponseHTTP (
    @SerializedName("message") var message: String?,
    @SerializedName("idAppointment") val idAppointment: String?,

    )
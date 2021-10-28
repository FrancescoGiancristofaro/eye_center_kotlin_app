package com.example.centro_oculistico.adapter

import com.google.gson.annotations.SerializedName

data class DoctorLogin (
    @SerializedName("doctorId") val doctorId : String?,
    @SerializedName("passwd") val passwd : String?
)
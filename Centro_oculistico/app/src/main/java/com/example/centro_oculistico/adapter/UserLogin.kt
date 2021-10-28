package com.example.centro_oculistico.adapter

import com.google.gson.annotations.SerializedName

data class UserLogin (
    @SerializedName("cdf") val cdf: String??,
    @SerializedName("passwd") val passwd: String?
    )
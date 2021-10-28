package com.example.centro_oculistico.adapter

import com.google.gson.annotations.SerializedName

data class User (
    @SerializedName("cdf") val cdf: String?,
    @SerializedName("name") val name: String?,
    @SerializedName("surname") val surname: String?,
    @SerializedName("gender") val gender: String?,
    @SerializedName("dateOfBirth") val dateOfBirth: String?,
    @SerializedName("email") val email: String?,
    @SerializedName("phoneNumber") val phoneNumber: String?,
    @SerializedName("passwd") val passwd: String?
)
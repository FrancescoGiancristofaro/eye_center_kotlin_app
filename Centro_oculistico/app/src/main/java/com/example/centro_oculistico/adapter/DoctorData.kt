package com.example.centro_oculistico.adapter

import com.google.gson.annotations.SerializedName


data class DoctorData (
    @SerializedName("doctorId") val doctorId : String?,
    @SerializedName("name") val name : String?,
    @SerializedName("surname") val surname : String?,
    @SerializedName("dateOfBirth") val dateOfBirth : String?,
    @SerializedName("email")val email : String?,
    @SerializedName("phoneNumber")val phoneNumber : String?,
    @SerializedName("headOffice") val headOffice : String?,
    @SerializedName("passwd") val passwd : String?,
    @SerializedName("gender")val gender : String?
    )
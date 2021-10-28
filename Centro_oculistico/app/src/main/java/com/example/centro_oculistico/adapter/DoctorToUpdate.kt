package com.example.centro_oculistico.adapter

import com.google.gson.annotations.SerializedName

data class DoctorToUpdate(
    @SerializedName("doctorId") val doctorId : String?,
    @SerializedName("email")val email : String?,
    @SerializedName("phoneNumber")val phoneNumber : String?,
    @SerializedName("passwd") val passwd : String?,
    @SerializedName("new_passwd")val new_passwd : String?
)

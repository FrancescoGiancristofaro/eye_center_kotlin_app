package com.example.centro_oculistico.adapter

import com.google.gson.annotations.SerializedName


data class AppointmentVolley (
     val cdf: String?,
    val doctorId: String?,
     val timeSlot: String?,
     val branchId: String?,
     val day: String?,
     val appointmentId : String?
        )
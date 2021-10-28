package com.example.centro_oculistico.adapter

import android.content.Context
import android.content.DialogInterface
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.centro_oculistico.R
import com.example.centro_oculistico.UpdateAppointmentDialog

class AppointmentDoctorItemAdapter(context: Context?, private val appointmentData : MutableList<AppointmentVolley>) : RecyclerView.Adapter<AppointmentDoctorItemAdapter.AppointmentDoctorItemViewHolder>(){
    val pref = context?.getSharedPreferences(
        "userDataPreferences",
        AppCompatActivity.MODE_PRIVATE
    )
    class AppointmentDoctorItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view){
        val textViewId: TextView = view.findViewById(R.id.appointmentId)
        val textViewDoctor: TextView = view.findViewById(R.id.appointmentDoctor)
        val textViewBranch: TextView = view.findViewById(R.id.appointmentBranch)
        val textViewDay: TextView = view.findViewById(R.id.appointmentDay)
        val textViewTimeSlot: TextView = view.findViewById(R.id.appointmentTimeSlot)



    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppointmentDoctorItemAdapter.AppointmentDoctorItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_appointment, parent, false)
        return AppointmentDoctorItemAdapter.AppointmentDoctorItemViewHolder(adapterLayout)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: AppointmentDoctorItemAdapter.AppointmentDoctorItemViewHolder, position: Int) {
        val item = appointmentData[position]
        holder.textViewId.text= item.appointmentId
        holder.textViewDoctor.text = item.doctorId
        holder.textViewBranch.text = item.branchId
        holder.textViewDay.text = item.day?.substring(0,4)+"-"+((item.day?.substring(5,7)?.toInt())?.plus(
            1
        )).toString()+"-"+item.day?.substring(8,10)
        holder.textViewTimeSlot.text = item.timeSlot

    }

    override fun getItemCount(): Int {
        return appointmentData.size
    }
}
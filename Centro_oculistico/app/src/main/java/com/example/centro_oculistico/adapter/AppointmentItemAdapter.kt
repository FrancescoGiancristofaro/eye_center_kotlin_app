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

class AppointmentItemAdapter(private val context: Context?, private val appointmentData : MutableList<AppointmentVolley>) : RecyclerView.Adapter<AppointmentItemAdapter.AppointmentItemViewHolder>(){
    val pref = context?.getSharedPreferences(
        "userDataPreferences",
        AppCompatActivity.MODE_PRIVATE
    )
    class AppointmentItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view){
        val textViewId: TextView = view.findViewById(R.id.appointmentId)
        val textViewDoctor: TextView = view.findViewById(R.id.appointmentDoctor)
        val textViewBranch: TextView = view.findViewById(R.id.appointmentBranch)
        val textViewDay: TextView = view.findViewById(R.id.appointmentDay)
        val textViewTimeSlot: TextView = view.findViewById(R.id.appointmentTimeSlot)



    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppointmentItemAdapter.AppointmentItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_appointment, parent, false)
        return AppointmentItemAdapter.AppointmentItemViewHolder(adapterLayout)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: AppointmentItemAdapter.AppointmentItemViewHolder, position: Int) {
        val item = appointmentData[position]
        holder.textViewId.text= item.appointmentId
        holder.textViewDoctor.text = item.doctorId
        holder.textViewBranch.text = item.branchId
        holder.textViewDay.text = item.day?.substring(0,4)+"-"+((item.day?.substring(5,7)?.toInt())?.plus(
            1
        )).toString()+"-"+item.day?.substring(8,10)
        holder.textViewTimeSlot.text = item.timeSlot
        holder.itemView.setOnClickListener {

            var fragmentManager = (it.context as FragmentActivity).supportFragmentManager
            val d = UpdateAppointmentDialog()
            val b = Bundle()
            b.putString("cdf",pref?.getString("cdf","default"))
            b.putString("doctorId",item.doctorId.toString())
            b.putString("appointmentId",item.appointmentId.toString())
            b.putString("branchId",item.branchId.toString())
            b.putString("day",item.day.toString())
            b.putString("timeSlot",item.timeSlot.toString())
            d.arguments = b
            d.show(fragmentManager,"UPDATEAPPOINTMENT")
            d.context1=it.context


        }
    }

    override fun getItemCount(): Int {
        return appointmentData.size
    }
}
package com.example.centro_oculistico
import com.example.centro_oculistico.adapter.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
interface Restapi {
    @Headers("Content-Type: application/json")
    @POST("user/read.php")
    fun loginUser(@Body userData: UserLogin): Call<List<User>>

    @Headers("Content-Type: application/json")
    @POST("doctor/read.php")
    fun loginDoctor(@Body doctorData: DoctorLogin) : Call<List<DoctorData>>

    @Headers("Content-Type: application/json")
    @POST("user/create.php")
    fun createUser(@Body user : User) : Call<ResponseHTTP>

    @Headers("Content-Type: application/json")
    @POST("appointment/create.php")
    fun createAppointment(@Body appointmentToCreate : AppointmentToCreate) : Call<ResponseHTTP>

    @Headers("Content-Type: application/json")
    @POST("appointment/update.php")
    fun updateAppointment(@Body appointmentToUpdate : Appointment) : Call<ResponseHTTP>

    @Headers("Content-Type: application/json")
    @POST("appointment/delete.php")
    fun deleteAppointment(@Body appointmentToDelete : AppointmentToDelete) : Call<ResponseHTTP>

    @Headers("Content-Type: application/json")
    @POST("user/update.php")
    fun updateUser(@Body userToUpdate: UserToUpdate) : Call<ResponseHTTP>

    @Headers("Content-Type: application/json")
    @POST("user/delete.php")
    fun deleteUser(@Body userToDelete: UserToDelete) : Call<ResponseHTTP>

    @Headers("Content-Type: application/json")
    @POST("doctor/update.php")
    fun updateDoctor(@Body doctorToUpdate: DoctorToUpdate) : Call<ResponseHTTP>

    @Headers("Content-Type: application/json")
    @POST("timeSlot/update.php")
    fun updateTimeSlot(@Body timeSlot: TimeSlot) : Call<ResponseHTTP>
}
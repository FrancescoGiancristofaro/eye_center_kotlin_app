
import com.example.centro_oculistico.Restapi
import com.example.centro_oculistico.ServiceBuilder
import com.example.centro_oculistico.adapter.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class RestApiService {
    fun loginUser(userData: UserLogin, onResult: (List<User>?) -> Unit){
        val retrofit = ServiceBuilder.buildService(Restapi::class.java)
        retrofit.loginUser(userData).enqueue(
            object : Callback<List<User>> {

                override fun onFailure(call: Call<List<User>>, t: Throwable) {

                    onResult(null)

                }
                override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {

                    val user = response.body()
                    onResult(user)
                }
            }
        )
    }

    fun loginDoctor(doctorLogin: DoctorLogin, onResult: (List<DoctorData>?) -> Unit){
        val retrofit = ServiceBuilder.buildService(Restapi::class.java)
        retrofit.loginDoctor(doctorLogin).enqueue(
            object : Callback<List<DoctorData>> {

                override fun onFailure(call: Call<List<DoctorData>>, t: Throwable) {

                    onResult(null)

                }
                override fun onResponse(call: Call<List<DoctorData>>, response: Response<List<DoctorData>>) {

                    val doctor = response.body()
                    onResult(doctor)
                }
            }
        )
    }


    fun createUser(user: User, onResult: (ResponseHTTP?) -> Unit){
        val retrofit = ServiceBuilder.buildService(Restapi::class.java)
        retrofit.createUser(user).enqueue(
            object : Callback<ResponseHTTP> {

                override fun onFailure(call: Call<ResponseHTTP>, t: Throwable) {

                    onResult(null)

                }
                override fun onResponse(call: Call<ResponseHTTP>, response: Response<ResponseHTTP>) {

                    var messageResponseError = ResponseHTTP(null,null)
                    var messageResponse = response.body()

                    if (response.code() == 503 || response.code() == 400 || response.code() == 404){
                        messageResponseError.message=
                            response.errorBody()?.string()?.replace('"',' ')?.replace("{","")?.replace("}","")?.replace("message","")?.replace(":","")?.replace("\u00e0","à")
                    }
                    if (messageResponseError.message!=null){
                        onResult(messageResponseError)
                    }else {
                        onResult(messageResponse)
                    }
                
                }
            }
        )
    }


    fun createAppointment(appointmentToCreate: AppointmentToCreate, onResult: (ResponseHTTP?) -> Unit){
        val retrofit = ServiceBuilder.buildService(Restapi::class.java)
        retrofit.createAppointment(appointmentToCreate).enqueue(
            object : Callback<ResponseHTTP> {

                override fun onFailure(call: Call<ResponseHTTP>, t: Throwable) {

                    onResult(null)

                }
                override fun onResponse(call: Call<ResponseHTTP>, response: Response<ResponseHTTP>) {
                    var messageResponseError = ResponseHTTP(null,null)
                    var messageResponse = response.body()

                    if (response.code() == 503){
                        messageResponseError.message=
                            response.errorBody()?.string()?.replace('"',' ')?.replace("{","")?.replace("}","")?.replace("message","")?.replace(":","")?.replace("\u00e0","à")
                    }
                    if (messageResponseError.message!=null){
                        onResult(messageResponseError)
                    }else {
                        onResult(messageResponse)
                    }                }
            }
        )
    }

    fun updateAppointment(appointmentToUpdate: Appointment, onResult: (ResponseHTTP?) -> Unit){
        val retrofit = ServiceBuilder.buildService(Restapi::class.java)
        retrofit.updateAppointment(appointmentToUpdate).enqueue(
            object : Callback<ResponseHTTP> {

                override fun onFailure(call: Call<ResponseHTTP>, t: Throwable) {

                    onResult(null)

                }
                override fun onResponse(call: Call<ResponseHTTP>, response: Response<ResponseHTTP>) {
                    var messageResponseError = ResponseHTTP(null,null)
                    var messageResponse = response.body()

                    if (response.code() == 503 || response.code() == 400 || response.code() == 404){
                        messageResponseError.message=
                            response.errorBody()?.string()?.replace('"',' ')?.replace("{","")?.replace("}","")?.replace("message","")?.replace(":","")?.replace("\u00e0","à")
                    }
                    if (messageResponseError.message!=null){
                        onResult(messageResponseError)
                    }else {
                        onResult(messageResponse)
                    }
                }
            }
        )
    }

    fun deleteAppointment(appointmentToDelete: AppointmentToDelete, onResult: (ResponseHTTP?) -> Unit){
        val retrofit = ServiceBuilder.buildService(Restapi::class.java)
        retrofit.deleteAppointment(appointmentToDelete).enqueue(
            object : Callback<ResponseHTTP> {
                override fun onFailure(call: Call<ResponseHTTP>, t: Throwable) {

                    onResult(null)

                }
                override fun onResponse(call: Call<ResponseHTTP>, response: Response<ResponseHTTP>) {
                    var messageResponseError = ResponseHTTP(null,null)
                    var messageResponse = response.body()

                    if (response.code() == 503 || response.code() == 400 || response.code() == 404){
                        messageResponseError.message=
                            response.errorBody()?.string()?.replace('"',' ')?.replace("{","")?.replace("}","")?.replace("message","")?.replace(":","")?.replace("\u00e0","à")
                    }
                    if (messageResponseError.message!=null){
                        onResult(messageResponseError)
                    }else {
                        onResult(messageResponse)
                    }
                }
            }
        )
    }

    fun updateUser(userToUpdate: UserToUpdate, onResult: (ResponseHTTP?) -> Unit){
        val retrofit = ServiceBuilder.buildService(Restapi::class.java)
        retrofit.updateUser(userToUpdate).enqueue(
            object : Callback<ResponseHTTP> {
                override fun onFailure(call: Call<ResponseHTTP>, t: Throwable) {

                    onResult(null)

                }
                override fun onResponse(call: Call<ResponseHTTP>, response: Response<ResponseHTTP>) {
                    var messageResponseError = ResponseHTTP(null,null)
                    var messageResponse = response.body()

                    if (response.code() == 503 || response.code() == 400 || response.code() == 404){
                        messageResponseError.message=
                            response.errorBody()?.string()?.replace('"',' ')?.replace("{","")?.replace("}","")?.replace("message","")?.replace(":","")?.replace("\u00e0","à")
                    }
                    if (messageResponseError.message!=null){
                        onResult(messageResponseError)
                    }else {
                        onResult(messageResponse)
                    }
                }
            }
        )
    }

    fun deleteUser(userToDelete: UserToDelete, onResult: (ResponseHTTP?) -> Unit){
        val retrofit = ServiceBuilder.buildService(Restapi::class.java)
        retrofit.deleteUser(userToDelete).enqueue(
            object : Callback<ResponseHTTP> {
                override fun onFailure(call: Call<ResponseHTTP>, t: Throwable) {

                    onResult(null)

                }
                override fun onResponse(call: Call<ResponseHTTP>, response: Response<ResponseHTTP>) {
                    var messageResponseError = ResponseHTTP(null,null)
                    var messageResponse = response.body()

                    if (response.code() == 503 || response.code() == 400 || response.code() == 404){
                        messageResponseError.message=
                            response.errorBody()?.string()?.replace('"',' ')?.replace("{","")?.replace("}","")?.replace("message","")?.replace(":","")?.replace("\u00e0","à")
                    }
                    if (messageResponseError.message!=null){
                        onResult(messageResponseError)
                    }else {
                        onResult(messageResponse)
                    }
                }
            }
        )
    }

    fun updateDoctor(doctorToUpdate: DoctorToUpdate, onResult: (ResponseHTTP?) -> Unit){
        val retrofit = ServiceBuilder.buildService(Restapi::class.java)
        retrofit.updateDoctor(doctorToUpdate).enqueue(
            object : Callback<ResponseHTTP> {
                override fun onFailure(call: Call<ResponseHTTP>, t: Throwable) {

                    onResult(null)

                }
                override fun onResponse(call: Call<ResponseHTTP>, response: Response<ResponseHTTP>) {
                    var messageResponseError = ResponseHTTP(null,null)
                    var messageResponse = response.body()

                    if (response.code() == 503 || response.code() == 400 || response.code() == 404){
                        messageResponseError.message=
                            response.errorBody()?.string()?.replace('"',' ')?.replace("{","")?.replace("}","")?.replace("message","")?.replace(":","")?.replace("\u00e0","à")
                    }
                    if (messageResponseError.message!=null){
                        onResult(messageResponseError)
                    }else {
                        onResult(messageResponse)
                    }
                }
            }
        )
    }

    fun updateTimeSlot(timeSlot: TimeSlot, onResult: (ResponseHTTP?) -> Unit){
        val retrofit = ServiceBuilder.buildService(Restapi::class.java)
        retrofit.updateTimeSlot(timeSlot).enqueue(
            object : Callback<ResponseHTTP> {
                override fun onFailure(call: Call<ResponseHTTP>, t: Throwable) {

                    onResult(null)

                }
                override fun onResponse(call: Call<ResponseHTTP>, response: Response<ResponseHTTP>) {
                    var messageResponseError = ResponseHTTP(null,null)
                    var messageResponse = response.body()

                    if (response.code() == 503 || response.code() == 400 || response.code() == 404){
                        messageResponseError.message=
                            response.errorBody()?.string()?.replace('"',' ')?.replace("{","")?.replace("}","")?.replace("message","")?.replace(":","")?.replace("\u00e0","à")
                    }
                    if (messageResponseError.message!=null){
                        onResult(messageResponseError)
                    }else {
                        onResult(messageResponse)
                    }
                }
            }
        )
    }
}
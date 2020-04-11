package com.google.ar.sceneform.samples.hellosceneform.server.requests

import android.util.EventLog
import com.google.ar.sceneform.samples.hellosceneform.server.api.API
import com.google.ar.sceneform.samples.hellosceneform.server.Constants
import com.google.ar.sceneform.samples.hellosceneform.server.model.bodies.RegistrationBody
import com.google.ar.sceneform.samples.hellosceneform.server.model.responses.RegistrationResponse
import org.greenrobot.eventbus.EventBus
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginRequest(
    private val name: String,
    private val password: String
) {

    fun execute() {
        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.serverUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(API::class.java)

        val body = RegistrationBody(
            name,
            password
        )
        val call = api.registerUser(body)
        call.enqueue(object : Callback<RegistrationResponse> {
            override fun onFailure(call: Call<RegistrationResponse>, t: Throwable) {
                println("FAIL RESPONCE == ${t.message}")
            }

            override fun onResponse(call: Call<RegistrationResponse>, response: Response<RegistrationResponse>) {
                if (response.isSuccessful) {
                    println("SUCCESS Login")
                    println(response.body()?.key)
                    EventBus.getDefault().post(RegistrationEvent(response.body()?.key))
                } else {
                    println("NO SUCCESS Login")
                }
            }
        })
    }

    class RegistrationEvent(
            val token: String?
    )

}
package com.sobol.testserverrequests.server.requests

import com.sobol.testserverrequests.server.Constants
import com.sobol.testserverrequests.server.api.API
import com.sobol.testserverrequests.server.model.bodies.RegistrationBody
import com.sobol.testserverrequests.server.model.responses.RegistrationResponse
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
                } else {
                    println("NO SUCCESS Login")
                }
            }
        })
    }

}
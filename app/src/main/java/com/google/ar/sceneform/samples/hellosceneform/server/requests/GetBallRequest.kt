package com.sobol.testserverrequests.server.requests

import com.sobol.testserverrequests.server.Constants
import com.sobol.testserverrequests.server.api.API
import com.sobol.testserverrequests.server.model.bodies.BallBody
import com.sobol.testserverrequests.server.model.bodies.RegistrationBody
import com.sobol.testserverrequests.server.model.responses.BallResponse
import com.sobol.testserverrequests.server.model.responses.RegistrationResponse
import com.sobol.testserverrequests.server.model.responses.StatusResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GetBallRequest(

) {

    fun execute() {
        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.serverUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(API::class.java)

        val call = api.getBall()
        call.enqueue(object : Callback<BallResponse> {
            override fun onFailure(call: Call<BallResponse>, t: Throwable) {
                println("FAIL RESPONCE == ${t.message}")
            }

            override fun onResponse(call: Call<BallResponse>, response: Response<BallResponse>) {
                if (response.isSuccessful) {
                    println("SUCCESS Get ball")
                    println(response.body()?.pos)
                    println(response.body()?.ang)
                } else {
                    println("NO SUCCESS Get ball")
                }
            }
        })
    }
}
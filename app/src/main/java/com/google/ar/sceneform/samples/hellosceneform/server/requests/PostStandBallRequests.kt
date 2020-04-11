package com.sobol.testserverrequests.server.requests

import com.sobol.testserverrequests.server.Constants
import com.sobol.testserverrequests.server.api.API
import com.sobol.testserverrequests.server.model.bodies.StandBallBody
import com.sobol.testserverrequests.server.model.bodies.StatusBody
import com.sobol.testserverrequests.server.model.responses.StandBallResponse
import com.sobol.testserverrequests.server.model.responses.StatusResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PostStandBallRequests(
    private val pos: ArrayList<Int>,
    private val ang: Int
) {

    fun execute() {
        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.serverUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(API::class.java)

        val body = StandBallBody(
            pos,
            ang
        )
        val call = api.postStandBall(body)
        call.enqueue(object : Callback<StandBallResponse> {
            override fun onFailure(call: Call<StandBallResponse>, t: Throwable) {
                println("FAIL RESPONCE == ${t.message}")
            }

            override fun onResponse(call: Call<StandBallResponse>, response: Response<StandBallResponse>) {
                if (response.isSuccessful) {
                    println("SUCCESS PostStandBall")
                    println(response.body()?.code)
                } else {
                    println("NO SUCCESS PostStandBall")
                }
            }
        })
    }

}
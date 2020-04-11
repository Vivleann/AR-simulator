package com.sobol.testserverrequests.server.requests

import com.sobol.testserverrequests.server.Constants
import com.sobol.testserverrequests.server.api.API
import com.sobol.testserverrequests.server.model.Figure
import com.sobol.testserverrequests.server.model.bodies.FiguresBody
import com.sobol.testserverrequests.server.model.bodies.StatusBody
import com.sobol.testserverrequests.server.model.responses.FiguresResponse
import com.sobol.testserverrequests.server.model.responses.PostFiguresResponse
import com.sobol.testserverrequests.server.model.responses.StatusResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PostFiguresRequest(
    private val sender: String,
    private val figures: Array<Figure>
) {

    fun execute() {
        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.serverUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(API::class.java)

        val body = FiguresBody(
            sender,
            figures
        )
        val call = api.postFigures(body)
        call.enqueue(object : Callback<PostFiguresResponse> {
            override fun onFailure(call: Call<PostFiguresResponse>, t: Throwable) {
                println("FAIL RESPONCE == ${t.message}")
            }

            override fun onResponse(call: Call<PostFiguresResponse>, response: Response<PostFiguresResponse>) {
                if (response.isSuccessful) {
                    println("SUCCESS PostFigures")
                } else {
                    println("NO SUCCESS PostFigures")
                }
            }
        })
    }

}
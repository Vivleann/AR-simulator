package com.google.ar.sceneform.samples.hellosceneform.server.requests

import com.google.ar.sceneform.samples.hellosceneform.server.api.API
import com.google.ar.sceneform.samples.hellosceneform.server.Constants
import com.google.ar.sceneform.samples.hellosceneform.server.model.Position
import com.google.ar.sceneform.samples.hellosceneform.server.model.bodies.StatusBody
import com.google.ar.sceneform.samples.hellosceneform.server.model.responses.PostStatusResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class PostStatusRequests(
    private val action: String,
    private val positions: Array<Position>
) {

    fun execute() {
        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.serverUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(API::class.java)

        val body = StatusBody(
            action,
            positions
        )
        val call = api.postData(body)
        call.enqueue(object : Callback<PostStatusResponse> {
            override fun onFailure(call: Call<PostStatusResponse>, t: Throwable) {
                println("FAIL RESPONCE == ${t.message}")
            }

            override fun onResponse(call: Call<PostStatusResponse>, response: Response<PostStatusResponse>) {
                if (response.isSuccessful) {
                    println("SUCCESS PostStatus")
                } else {
                    println("NO SUCCESS PostStatus")
                }
            }
        })
    }

}
package com.google.ar.sceneform.samples.hellosceneform.server.requests

import com.google.ar.sceneform.samples.hellosceneform.server.api.API
import com.google.ar.sceneform.samples.hellosceneform.server.Constants
import com.google.ar.sceneform.samples.hellosceneform.server.model.Figure
import com.google.ar.sceneform.samples.hellosceneform.server.model.bodies.FiguresBody
import com.google.ar.sceneform.samples.hellosceneform.server.model.responses.PostFiguresResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PostFiguresRequest(
    private val sender: String,
    private val figures: ArrayList<Figure>
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
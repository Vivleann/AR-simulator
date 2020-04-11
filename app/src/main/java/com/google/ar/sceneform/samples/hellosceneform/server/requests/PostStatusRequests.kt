package com.sobol.testserverrequests.server.requests

import com.sobol.testserverrequests.server.Constants
import com.sobol.testserverrequests.server.Constants.serverUrl
import com.sobol.testserverrequests.server.api.API
import com.sobol.testserverrequests.server.model.Position
import com.sobol.testserverrequests.server.model.bodies.RegistrationBody
import com.sobol.testserverrequests.server.model.bodies.StatusBody
import com.sobol.testserverrequests.server.model.responses.PostStatusResponse
import com.sobol.testserverrequests.server.model.responses.RegistrationResponse
import com.sobol.testserverrequests.server.model.responses.StatusResponse
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
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
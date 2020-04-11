package com.google.ar.sceneform.samples.hellosceneform.server.api

import com.google.ar.sceneform.samples.hellosceneform.server.model.responses.*
import com.google.ar.sceneform.samples.hellosceneform.server.model.bodies.FiguresBody
import com.google.ar.sceneform.samples.hellosceneform.server.model.bodies.RegistrationBody
import com.google.ar.sceneform.samples.hellosceneform.server.model.bodies.StandBallBody
import com.google.ar.sceneform.samples.hellosceneform.server.model.bodies.StatusBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface API {

    @POST("auth/login/")
    fun registerUser(@Body registrationBody: RegistrationBody): Call<RegistrationResponse>

    @Headers("Authorization: Token a015453030a8d38e9d185c646416e6e275ad6e09")
    @GET("game/status/")
    fun getData(): Call<StatusResponse>

    @Headers("Authorization: Token a015453030a8d38e9d185c646416e6e275ad6e09")
    @POST("game/status/")
    fun postData(@Body statusBody: StatusBody): Call<PostStatusResponse>

    @Headers("Authorization: Token a015453030a8d38e9d185c646416e6e275ad6e09")
    @POST("game/figures/")
    fun postFigures(@Body figuresBody: FiguresBody): Call<PostFiguresResponse>

    @Headers("Authorization: Token a015453030a8d38e9d185c646416e6e275ad6e09")
    @POST("game/stand/ball/")
    fun postStandBall(@Body standBallBody: StandBallBody): Call<StandBallResponse>

    @Headers("Authorization: Token a015453030a8d38e9d185c646416e6e275ad6e09")
    @GET("game/ball/")
    fun getBall(): Call<BallResponse>

    @Headers("Authorization: Token a015453030a8d38e9d185c646416e6e275ad6e09")
    @GET("game/figures/")
    fun getFigures(): Call<FiguresResponse>

}
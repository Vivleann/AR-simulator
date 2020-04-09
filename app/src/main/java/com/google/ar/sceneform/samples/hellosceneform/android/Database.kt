package com.google.ar.sceneform.samples.hellosceneform.android

class Database {

    private object Holder {
        val INSTANCE = Database()
    }

    companion object {
        val instance: Database by lazy { Holder.INSTANCE }
    }

    private val polygonIcTeamPrefix = "polygon_team1_"
    private var polygonIcCnt = 0

    fun getPolygonIcPrefix(): String {
        val result = "$polygonIcTeamPrefix$polygonIcCnt.png"
        polygonIcCnt++
        return result
    }

}
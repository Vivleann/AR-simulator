package com.google.ar.sceneform.samples.hellosceneform.android

import android.app.Application

class ThisApp : Application() {

    companion object {
        lateinit var instance: ThisApp
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        PreferencesUtils.setSharedPreferences(this)
    }

}
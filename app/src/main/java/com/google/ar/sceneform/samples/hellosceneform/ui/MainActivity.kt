package com.google.ar.sceneform.samples.hellosceneform.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import com.google.ar.sceneform.samples.hellosceneform.android.AndroidUtils
import com.google.ar.sceneform.samples.hellosceneform.android.PreferencesUtils
import com.google.ar.sceneform.samples.hellosceneform.R
import java.lang.Exception
import android.support.v4.content.ContextCompat.startActivity
import android.content.Intent
import com.google.ar.sceneform.samples.hellosceneform.ar.ArActivity
import com.google.ar.sceneform.samples.hellosceneform.server.requests.LoginRequest

class MainActivity : AppCompatActivity() {

    private var waitingFragment: WaitingFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ui)

        if (PreferencesUtils.getString(PreferencesUtils.LOGIN_KEY) != "" &&
                PreferencesUtils.getString(PreferencesUtils.PASSWORD_KEY) != "") {
            //todo: start game
            openWaitingFragment(WaitingFragment.GAME_START)
        } else {
            changeFragment(LoginFragment(), R.id.main_content)
        }

                //LoginRequest("BananaDev", "4nN-qJE-hHp-8Lm").execute()
    }

    fun onGameConnected() {
//        waitingFragment!!.dismiss()
       // waitingFragment = null
        val myIntent = Intent(this, ArActivity::class.java)
        startActivity(myIntent)
    }

    fun changeFragment(fragment: Fragment, frame: Int) {
        try {
            val fragmentManager = supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(frame, fragment)
            fragmentTransaction.commit()
        } catch (e: Exception) {
            AndroidUtils.showProblemToast(this)
            println(e)
        }
    }

    fun openWaitingFragment(title: String) {
        waitingFragment = WaitingFragment(title)
        waitingFragment!!.show(supportFragmentManager, WaitingFragment.TAG)
    }
}

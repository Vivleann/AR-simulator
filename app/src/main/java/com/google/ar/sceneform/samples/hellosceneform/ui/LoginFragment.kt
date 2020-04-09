package com.google.ar.sceneform.samples.hellosceneform.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.google.ar.sceneform.samples.hellosceneform.R
import com.google.ar.sceneform.samples.hellosceneform.android.PreferencesUtils
import kotlinx.android.synthetic.main.fragment_login.*


class LoginFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        login_btn.setOnClickListener {
            onButtonClick()
        }
    }

    private fun onButtonClick() {
        val login = login_edit_text.text.toString()
        val password = password_edit_text.text.toString()
        if (login != "" && password != "") {
            (context as MainActivity).onGameConnected()
            if (checkbox.isChecked) {
                PreferencesUtils.saveString(PreferencesUtils.LOGIN_KEY, login)
                PreferencesUtils.saveString(PreferencesUtils.PASSWORD_KEY, password)
            }
        } else {
            Toast.makeText(context, "Enter your login and password!", Toast.LENGTH_LONG).show()
        }
    }

}

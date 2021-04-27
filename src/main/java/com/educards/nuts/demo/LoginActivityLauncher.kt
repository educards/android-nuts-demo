package com.educards.nuts.demo

import android.app.Activity
import android.content.Intent
import com.educards.nuts.retrofit2.DefaultAuthTokenProvider.AuthLauncher

class LoginActivityLauncher: AuthLauncher {

    override fun startAuthentication(parentActivity: Activity) {
        val intent = Intent(parentActivity, LoginActivity::class.java)
        parentActivity.startActivity(intent)
    }

}

package com.educards.nuts.demo

import android.app.Activity
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.educards.nuts.demo.dto.DemoLoginResponseDTO
import com.educards.nuts.retrofit2.TemplateCall
import com.educards.nuts.retrofit2.TemplateCallback
import com.educards.nuts.ui.TemplateData

class LoginActivityModel(application: Application) : AndroidViewModel(application) {

    val loginData = TemplateData<DemoLoginResponseDTO>()

    fun callLogin(activity: Activity, login: String, password: String): TemplateCall<DemoLoginResponseDTO> {
        val app = getApplication() as NutsDemoApplication
        val call = app.demoService.demoLogin(login, password)
        call.enqueue(activity, loginData, TemplateCallback<DemoLoginResponseDTO>())
        return call
    }

    companion object {
        private const val TAG: String = "LoginActivityModel"
    }

}

package com.educards.nuts.demo

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.educards.nuts.AuthToken
import com.educards.nuts.demo.databinding.ActivityLoginBinding
import com.educards.nuts.demo.dto.DemoLoginResponseDTO
import java.net.URI

class LoginActivity: AppCompatActivity() {

    private val model: LoginActivityModel by viewModels()

    private var loginUri: URI? = null

    private val binding: ActivityLoginBinding by lazy {
        DataBindingUtil.inflate(layoutInflater, R.layout.activity_login, null, false ) as ActivityLoginBinding
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        // observe login server request
        val loginNutsBinder =
            SnackbarTemplateBinder<DemoLoginResponseDTO>(
                this,
                binding,
                model.loginData,
                this
            )

//        loginNutsBinder.setupRequestInProgressBehavior(
//            binding.operationTypesProgressBar,
//            binding.operationTypesRecyclerView
//        )

        loginNutsBinder.observeRequestSucceeded(fun (response) {
            val nutsApp = application as NutsDemoApplication
            val authToken = toAuthToken(nutsApp, loginUri!!, response)
            nutsApp.authTokenStorage.saveAuthToken(authToken)
        })

    }

    fun callLogin(view: View) {
        // TODO replace with typed in data
        val call = model.callLogin(this, "test_login", "test_password")
        loginUri = call.uri
    }

    private fun toAuthToken(nutsApp: NutsDemoApplication, loginUri: URI, loginResponseDto: DemoLoginResponseDTO): AuthToken {

        val expires = calculateExpirationMs(loginResponseDto, nutsApp)

        val userCredentials = AuthToken.UserCredentials(
            loginResponseDto.firstName, loginResponseDto.lastName,
            loginResponseDto.login, loginResponseDto.email)

        return AuthToken(loginUri, loginResponseDto.jSessionId, expires, userCredentials)
    }

    private fun calculateExpirationMs(loginResponseDto: DemoLoginResponseDTO, nutsApp: NutsDemoApplication): Long {
        return if (loginResponseDto.sessionMaxInactiveInterval != null) {
            val maxInactiveIntervalMs = loginResponseDto.sessionMaxInactiveInterval.toLong() * 1000
            nutsApp.authTimeService.now() + maxInactiveIntervalMs
        } else {
            Long.MAX_VALUE
        }
    }

    companion object {
        private const val TAG: String = "LoginActivity"
    }
}

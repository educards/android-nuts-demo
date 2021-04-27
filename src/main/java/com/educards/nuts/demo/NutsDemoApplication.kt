package com.educards.nuts.demo

import android.app.Application
import com.educards.nuts.AuthTimeService
import com.educards.nuts.AuthToken
import com.educards.nuts.AuthTokenStorage
import com.educards.nuts.demo.server.DemoService
import com.educards.nuts.retrofit2.DefaultAuthTokenProvider
import com.educards.nuts.retrofit2.DefaultRetrofitBuilder
import com.educards.nuts.retrofit2.PersistentHttpCookieStore
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import java.io.IOException
import java.lang.RuntimeException
import java.util.*

class NutsDemoApplication: Application() {

    val objectMapper: ObjectMapper by lazy {
        val mapper = ObjectMapper()
        mapper.registerModule(KotlinModule()) // to support Kotlin 'data class' marshalling
        mapper
    }

    val cookieStore: PersistentHttpCookieStore by lazy {
        PersistentHttpCookieStore(this)
    }

    val authTimeService: AuthTimeService = object : AuthTimeService {
        override fun now(): Long {
            return System.currentTimeMillis()
        }
    }

    val loginActivityLauncher = LoginActivityLauncher()

    var authToken: AuthToken? = null
    val authTokenStorage: AuthTokenStorage = object : AuthTokenStorage {
        override fun getAuthToken(): AuthToken? {
            return this@NutsDemoApplication.authToken
        }
        override fun saveAuthToken(authToken: AuthToken) {
            this@NutsDemoApplication.authToken = authToken
        }
        override fun removeAuthToken() {
            this@NutsDemoApplication.authToken = null
        }
        override fun isTokenValid(now: Long): Boolean {
            val token = this@NutsDemoApplication.authToken
            return token != null && !token.isExpired(now)
        }
    }

    val demoService: DemoService by lazy {
        val authTokenProvider = DefaultAuthTokenProvider(authTokenStorage, loginActivityLauncher, authTimeService, objectMapper, cookieStore)
        DefaultRetrofitBuilder(this, readServerApiBaseUrl(), objectMapper, authTokenProvider)
            .retrofit.create(DemoService::class.java)
    }

    /**
     * Reads server API URL from 'environment.properties' file.
     */
    private fun readServerApiBaseUrl(): String? {
        val key = "server.api.baseUrl"
        val propertyFile = "environment.properties"
        val properties = Properties()
        try {
            properties.load(assets.open(propertyFile))
        } catch (e: IOException) {
            throw RuntimeException("Failed to load property [property=$key, propertyFile=$propertyFile]", e)
        }
        return properties.getProperty(key)
    }

}

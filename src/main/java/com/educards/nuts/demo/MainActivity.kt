package com.educards.nuts.demo

import android.app.Activity
import android.os.Bundle
import androidx.databinding.DataBindingUtil.inflate
import com.educards.nuts.demo.databinding.ActivityMainBinding

class MainActivity: Activity() {

    private val binding: ActivityMainBinding by lazy {
        inflate(layoutInflater, R.layout.activity_main, null, false) as ActivityMainBinding
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

}

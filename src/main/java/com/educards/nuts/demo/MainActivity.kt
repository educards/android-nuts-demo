package com.educards.nuts.demo

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil.inflate
import com.educards.nuts.demo.databinding.ActivityMainBinding
import com.educards.nuts.demo.dto.DemoSampleDataDTO

class MainActivity: AppCompatActivity() {

    private val model: MainActivityModel by viewModels()

    private val binding: ActivityMainBinding by lazy {
        inflate(layoutInflater, R.layout.activity_main, null, false) as ActivityMainBinding
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // observe sample data fetching
        val fetchSampleDataNutsBinder =
            SnackbarTemplateBinder<DemoSampleDataDTO>(
                this,
                binding,
                model.sampleData,
                this
            )
//        fetchSampleDataNutsBinder.setupRequestInProgressBehavior(
//            binding.operationTypesProgressBar,
//            binding.operationTypesRecyclerView
//        )
        fetchSampleDataNutsBinder.observeRequestSucceeded(fun (sampleData) {
            Log.i(TAG, "$sampleData")
            // TODO update UI
        })
    }

    fun fetchSampleData(view: View) {
        model.fetchSampleData(this)
    }

    companion object {
        private const val TAG: String = "MainActivity"
    }

}

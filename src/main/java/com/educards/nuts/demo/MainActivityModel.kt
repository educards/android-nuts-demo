package com.educards.nuts.demo

import android.app.Activity
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.educards.nuts.demo.dto.DemoSampleDataDTO
import com.educards.nuts.retrofit2.TemplateCallback
import com.educards.nuts.ui.TemplateData

class MainActivityModel(application: Application) : AndroidViewModel(application) {

    val sampleData = TemplateData<DemoSampleDataDTO>()

    fun fetchSampleData(activity: Activity) {
        val app = getApplication() as NutsDemoApplication
        app.demoService.demoSampleData.enqueue(activity, sampleData, TemplateCallback<DemoSampleDataDTO>())
    }

    companion object {
        private const val TAG: String = "MainActivityModel"
    }

}

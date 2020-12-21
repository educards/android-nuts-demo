package com.educards.nuts.demo

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel

class MainActivityModel(application: Application) : AndroidViewModel(application) {

    fun fetchSampleData() {
        // TODO Use Nuts API to fetch Sample data from public server
        Log.i(TAG, "fetchSampleData() called")
    }

    companion object {
        private const val TAG: String = "MainActivityModel"
    }

}

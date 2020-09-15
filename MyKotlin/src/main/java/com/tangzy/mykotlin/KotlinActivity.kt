package com.tangzy.mykotlin

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class KotlinActivity : AppCompatActivity() {

    private val TAG = "KotlinActivity"

    private lateinit var testBeanK: TestBeanK

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!this::testBeanK.isInitialized || testBeanK == null){
            Log.d(TAG, "testBeanK == null")
        }else{
            Log.d(TAG, "testBeanK != null")

        }
    }

}
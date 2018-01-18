package com.eda.androidsamples

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import com.eda.androidsamples.databinding.ActivityActivityTransitionTestOpenedBinding

/**
 * Created by kobayashiryou on 2018/01/17.
 *
 * Activity間Transitionテストで開かれる側の画面
 */

class ActivityTransitionTestOpenedActivity : AppCompatActivity() {

    private lateinit var binding: ActivityActivityTransitionTestOpenedBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_activity_transition_test_opened)

        binding.back.setOnClickListener {
            ActivityCompat.finishAfterTransition(this)
        }
    }

//    override fun finish() {
//        supportFinishAfterTransition()
//    }
}

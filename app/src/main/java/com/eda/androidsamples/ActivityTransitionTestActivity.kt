package com.eda.androidsamples

import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.view.ViewCompat
import android.support.v7.app.AppCompatActivity
import com.eda.androidsamples.databinding.ActivityActivityTransitionTestBinding

/**
 * Created by kobayashiryou on 2018/01/17.
 *
 * Activity間Transitionテスト画面
 */

class ActivityTransitionTestActivity : AppCompatActivity() {

    private lateinit var binding: ActivityActivityTransitionTestBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_activity_transition_test)

        binding.start.setOnClickListener {
            val intent = Intent(this, ActivityTransitionTestOpenedActivity::class.java)
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this,
                binding.sharedText, ViewCompat.getTransitionName(binding.sharedText)
//                Pair(binding.sharedText, ViewCompat.getTransitionName(binding.sharedText)),
//                Pair(binding.sharedText, ViewCompat.getTransitionName(binding.sharedText))
            )
            startActivity(intent, options.toBundle())
        }
    }
}

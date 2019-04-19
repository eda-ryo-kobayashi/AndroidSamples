package com.eda.androidsamples

import android.content.Intent
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import com.eda.androidsamples.databinding.ActivityActivityTransitionTestBinding

import androidx.core.util.Pair as AndroidPair

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

            // Kotlinで複数のSharedElementを扱うのめんどくさい問題

            // *arrayListOfの*の意味は「Collectionの中身をバラバラにする」という意味
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this,
//                binding.sharedText, ViewCompat.getTransitionName(binding.sharedText)
                *arrayListOf(
                    AndroidPair<View, String>(binding.sharedText, ViewCompat.getTransitionName(binding.sharedText)),
                    AndroidPair<View, String>(binding.bg, ViewCompat.getTransitionName(binding.bg))
                ).toTypedArray()
            )
            startActivity(intent, options.toBundle())
        }
    }
}

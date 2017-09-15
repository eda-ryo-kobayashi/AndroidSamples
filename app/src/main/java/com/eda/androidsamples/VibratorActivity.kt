package com.eda.androidsamples

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.eda.androidsamples.vibrator.VibratorUtil

/**
 * Created by kobayashiryou on 2017/09/13.
 *
 * バイブレーターを使う
 */
class VibratorActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vibrator)

        findViewById<View>(R.id.one_shot).setOnClickListener {
            VibratorUtil.oneShot(this, 100, 64)
        }

        findViewById<View>(R.id.two).setOnClickListener {
            val timings = longArrayOf(500, 1000, 1000, 1000)
            VibratorUtil.waveForm(this, timings)
        }

        findViewById<View>(R.id.infinite).setOnClickListener {
            val timings = longArrayOf(500, 1000, 1000, 1000)
            // timingsの[2]から最後までを繰り返す
            VibratorUtil.waveForm(this, timings, 2)
        }

        findViewById<View>(R.id.five_seconds).setOnClickListener {
            VibratorUtil.oneShot(this, 5000)
        }

        findViewById<View>(R.id.cancel).setOnClickListener {
            VibratorUtil.cancel(this)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        VibratorUtil.cancel(this)
    }
}
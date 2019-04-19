package com.eda.androidsamples.vibrator

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.annotation.IntRange

/**
 * Created by kobayashiryou on 2017/09/14.
 *
 * バイブレータ処理
 */
class VibratorUtil private constructor() {

    companion object {

        private val INVALID_AMPLITUDE = -2

        fun oneShot(context: Context, millisecond: Long, @IntRange(from = 0, to = 255) amplitude: Int = INVALID_AMPLITUDE) {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                var amp = amplitude
                if(amp == INVALID_AMPLITUDE) {
                    amp = VibrationEffect.DEFAULT_AMPLITUDE
                }
                vibrator(context).vibrate(VibrationEffect.createOneShot(millisecond, amp))
            } else {
                vibrator(context).vibrate(millisecond)
            }
        }

        fun waveForm(context: Context, timings: LongArray, @IntRange(from = 0) repeat: Int = -1) {
            val rep = if(repeat < timings.size) repeat else timings.size - 1
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator(context).vibrate(VibrationEffect.createWaveform(timings, rep))
            } else {
                vibrator(context).vibrate(timings, rep)
            }
        }

        fun cancel(context: Context) {
            vibrator(context).cancel()
        }

        private fun vibrator(context: Context): Vibrator {
            return context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        }
    }
}
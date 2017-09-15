package com.eda.androidsamples

import android.content.Context
import android.hardware.*
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.PowerManager
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Display
import android.view.Surface
import android.view.View
import android.view.WindowManager
import android.widget.TextView

/**
 * Created by kobayashiryou on 2017/09/13.
 *
 * 加速度センサー
 */
class AccelerometerActivity : AppCompatActivity() {

    // デバイスセンサー
    private lateinit var sensorMgr: SensorManager
    // 加速度
    private lateinit var accel: Sensor
    // 画面回転
    private lateinit var display: Display

    private lateinit var sim: SensorSimulator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_accelerometer)

        sensorMgr = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        display = (getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay

        val x = findViewById<TextView>(R.id.x)
        val y = findViewById<TextView>(R.id.y)
        val z = findViewById<TextView>(R.id.z)
        val target = findViewById<View>(R.id.target)

        sim = SensorSimulator(x, y, z, target, display)

        accel = sensorMgr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    }

    override fun onResume() {
        super.onResume()
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        sensorMgr.registerListener(sim, accel, SensorManager.SENSOR_DELAY_GAME, Handler(Looper.getMainLooper()))
    }

    override fun onPause() {
        super.onPause()
        window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        sensorMgr.unregisterListener(sim, accel)
    }

    private class SensorSimulator(
        val x: TextView,
        val y: TextView,
        val z: TextView,
        val target: View,
        val display: Display
    ) : SensorEventListener, SensorEventListener2 {

        var vx: Float = 0.0f
        var vy: Float = 0.0f
        var vz: Float = 1.0f

        override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
            Log.i("SensorSimulator", "onAccuracyChanged")
        }

        override fun onSensorChanged(p0: SensorEvent?) {
            //Log.i("SensorSimulator", "onSensorChanged")

            if(p0?.sensor?.type != Sensor.TYPE_ACCELEROMETER) return

            var oldZ = vz

            if (p0.values?.size!! >= 3) {
                vx = p0.values?.get(0)!!
                vy = p0.values?.get(1)!!
                vz = p0.values?.get(2)!!

                when(display.rotation) {
                    Surface.ROTATION_0 -> { }

                    Surface.ROTATION_90 -> {
                        val tmpY = vy
                        vy = vx
                        vx = -tmpY
                    }

                    Surface.ROTATION_180 -> {
                        vx = -vx
                        vy = -vy
                    }

                    Surface.ROTATION_270 -> {
                        val tmpY = vy
                        vy = -vx
                        vx = tmpY
                    }
                }

                x.text = String.format("%.2f", vx)
                y.text = String.format("%.2f", vy)
                z.text = String.format("%.2f", vz)
            }

            target.x -= vx
            target.y += vy
            if(vz - oldZ < -4.0f) {
                target.scaleX -= vz * 0.2f
                target.scaleY -= vz * 0.2f
            } else {
                target.scaleX -= vz * 0.001f
                target.scaleY -= vz * 0.001f
            }
            if(target.scaleX <= 0.1f) {
                target.scaleX = 0.1f
                target.scaleY = 0.1f
            }
        }

        override fun onFlushCompleted(p0: Sensor?) {
            Log.i("SensorSimulator", "onFlushCompleted")
        }

    }
}
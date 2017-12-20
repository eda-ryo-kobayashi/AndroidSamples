package com.eda.androidsamples

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ImageView
import com.wonderkiln.camerakit.*

/**
 * Created by kobayashiryou on 2017/09/13.
 *
 * Camera-Kitを使ってカメラを使う
 */
class CameraKitActivity : AppCompatActivity() {

    private lateinit var camView: CameraView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera_kit)

        camView = findViewById(R.id.camera)
//        camView.setCameraListener(object : CameraListener() {
//            override fun onPictureTaken(jpeg: ByteArray?) {
//                super.onPictureTaken(jpeg)
//
//                val capImg = BitmapFactory.decodeByteArray(jpeg, 0, jpeg?.size!!)
//                runOnUiThread { findViewById<ImageView>(R.id.preview).setImageBitmap(capImg) }
//            }
//        })
        camView.addCameraKitListener(object: CameraKitEventListener {
            override fun onVideo(p0: CameraKitVideo?) {
            }

            override fun onEvent(p0: CameraKitEvent?) {
            }

            override fun onImage(p0: CameraKitImage?) {
                runOnUiThread { findViewById<ImageView>(R.id.preview).setImageBitmap(p0?.bitmap) }
            }

            override fun onError(p0: CameraKitError?) {
            }

        })

        findViewById<View>(R.id.capture).setOnClickListener {
            camView.captureImage()
        }
    }

    override fun onResume() {
        super.onResume()
        camView.start()
    }

    override fun onPause() {
        camView.stop()
        super.onPause()
    }
}
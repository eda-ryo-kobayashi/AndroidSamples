package com.eda.androidsamples

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ImageView

/**
 * Created by kobayashiryou on 2017/09/12.
 *
 * カメラキャプチャ
 */
class CameraCaptureActivity : AppCompatActivity() {

    companion object {
        val REQ_IMG_CAPTURE = 12314
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera_capture)

        findViewById<View>(R.id.capture).setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            if(intent.resolveActivity(packageManager) != null) {
                startActivityForResult(intent, REQ_IMG_CAPTURE)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == REQ_IMG_CAPTURE && resultCode == RESULT_OK) {
            val img = data?.extras?.get("data") as Bitmap
            findViewById<ImageView>(R.id.image).setImageBitmap(img)
        }
    }
}
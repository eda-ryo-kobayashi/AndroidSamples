package com.eda.androidsamples

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.SurfaceTexture
import android.hardware.camera2.*
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.util.Size
import android.view.Surface
import android.widget.Switch

/**
 * Created by kobayashiryou on 2017/09/13.
 *
 * フラッシュライトを点けるだけ
 */
class FlashLightActivity : AppCompatActivity() {

    private lateinit var camMgr: CameraManager
    private var session: CameraCaptureSession? = null
    private var reqBuilder: CaptureRequest.Builder? = null
    private var camDev: CameraDevice? = null
    private var cameraInitialized = false
    private var surfaceTexture: SurfaceTexture? = null
    private var surface: Surface? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flash_light)

        initCamera()

        findViewById<Switch>(R.id.light_switch).setOnCheckedChangeListener { _, checked ->
            if(!cameraInitialized) return@setOnCheckedChangeListener
            if(checked) {
                reqBuilder?.set(CaptureRequest.FLASH_MODE, CameraMetadata.FLASH_MODE_TORCH)
            } else {
                reqBuilder?.set(CaptureRequest.FLASH_MODE, CameraMetadata.FLASH_MODE_OFF)
            }
            session?.setRepeatingRequest(reqBuilder?.build(), null, null)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        close()
    }

    @SuppressLint("MissingPermission")
    private fun initCamera() {
        camMgr = getSystemService(Context.CAMERA_SERVICE) as CameraManager
        val ca = camMgr.getCameraCharacteristics("0")
        val flashAvailable = ca.get(CameraCharacteristics.FLASH_INFO_AVAILABLE)
        if(flashAvailable) {
            camMgr.openCamera("0",
                object : CameraDevice.StateCallback() {
                    override fun onOpened(p0: CameraDevice?) {
                        camDev = p0

                        reqBuilder = camDev?.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW)
                        reqBuilder?.set(CaptureRequest.CONTROL_AE_MODE, CameraMetadata.CONTROL_AF_MODE_AUTO)
                        reqBuilder?.set(CaptureRequest.FLASH_MODE, CameraMetadata.FLASH_MODE_OFF)
                        val list = arrayListOf<Surface>()
                        surfaceTexture = SurfaceTexture(1)
                        val size = getSmallestSize(camDev?.id!!)
                        surfaceTexture?.setDefaultBufferSize(size.width, size.height)
                        surface = Surface(surfaceTexture)
                        list.add(surface!!)
                        reqBuilder?.addTarget(surface)
                        camDev?.createCaptureSession(list,
                            object : CameraCaptureSession.StateCallback() {
                                override fun onConfigureFailed(p0: CameraCaptureSession?) {
                                }

                                override fun onConfigured(p0: CameraCaptureSession?) {
                                    session = p0
                                    session?.setRepeatingRequest(reqBuilder?.build(), null, null)
                                }

                            },
                            null)
                        cameraInitialized = true
                    }

                    override fun onDisconnected(p0: CameraDevice?) {
                    }

                    override fun onError(p0: CameraDevice?, p1: Int) {
                    }

                },
            null)
        } else {

        }
    }

    private fun getSmallestSize(cameraId: String): Size {
        val outputSizes = camMgr.getCameraCharacteristics(cameraId)
            .get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP)
            .getOutputSizes(SurfaceTexture::class.java)

        if(outputSizes == null || outputSizes.isEmpty()) {
            // exception
        }

        var chosen = outputSizes[0]
        outputSizes.forEach { o ->
            if(chosen.width >= o.width && chosen.height >= o.height) {
                chosen = o
            }
        }

        return chosen
    }

    private fun close() {
        session?.close()
        camDev?.close()
        session = null
        camDev = null
    }
}
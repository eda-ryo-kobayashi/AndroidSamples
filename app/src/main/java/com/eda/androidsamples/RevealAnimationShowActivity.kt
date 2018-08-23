package com.eda.androidsamples

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.TextView

/**
 * Created by kobayashiryou on 2018/01/12.
 *
 * RevealAnimation表示画面
 */
class RevealAnimationShowActivity : AppCompatActivity() {

  companion object {
    private val KEY_COLOR = "color"
    private val KEY_X = "x"
    private val KEY_Y = "y"

    fun createIntent(context: Context, color: Int, x: Float, y: Float): Intent =
        Intent(context, RevealAnimationShowActivity::class.java)
            .putExtra(KEY_COLOR, color)
            .putExtra(KEY_X, x)
            .putExtra(KEY_Y, y)
  }

  private lateinit var bg: View

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_reveal_animation_show)

    val color = intent.getIntExtra(KEY_COLOR, 0xFFFFFFFF.toInt())

    // 背景色設定
    bg = findViewById(R.id.bg)
    bg.setBackgroundColor(color)

    val l = luminance(color)
    if (l <= 120) {
      // 背景暗い
      findViewById<TextView>(R.id.text).setTextColor(Color.WHITE)
    } else {
      // 背景明るい
      findViewById<TextView>(R.id.text).setTextColor(Color.BLACK)
    }

    runOnUiThread {
      AnimUtility.startCircularRevealAnimation(
          bg,
          intent.getFloatExtra(KEY_X, 0f).toInt(),
          intent.getFloatExtra(KEY_Y, 0f).toInt(),
          color and 0x00FFFFFF, color, 500)
    }
  }

  private var backPressed = false
  override fun onBackPressed() {
    if (backPressed) return
    backPressed = true
    val startColor = intent.getIntExtra(KEY_COLOR, -0x1)
    AnimUtility.startCircularExitAnimation(
        bg,
        intent.getFloatExtra(KEY_X, 0f).toInt(),
        intent.getFloatExtra(KEY_Y, 0f).toInt(),
        startColor, startColor and 0x00FFFFFF, 500) {
      bg.visibility = View.GONE
      finish()
    }
  }

  private fun luminance(color: Int): Double {
    val r = color shr 16 and 0xFF
    val g = color shr 8 and 0xFF
    val b = color and 0xFF
    return r * 0.298912 +
        g * 0.586611 +
        b * 0.114478
  }
}
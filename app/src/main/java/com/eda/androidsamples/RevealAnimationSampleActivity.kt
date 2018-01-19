package com.eda.androidsamples

import android.content.Intent
import android.graphics.Color
import android.graphics.Rect
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MotionEvent
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import io.reactivex.functions.Function

/**
 * Created by kobayashiryou on 2018/01/12.
 *
 * RevealAnimationサンプル
 */

class RevealAnimationSampleActivity : AppCompatActivity() {

    private class Item(val label: String,
                       val creator: Function<RevealAnimationSampleActivity, Intent>) {
        override fun toString(): String = label
    }

    companion object {
        private val ITEMS = arrayOf(
            Item("赤", newIntent(Color.RED)),
            Item("緑", newIntent(Color.GREEN)),
            Item("青", newIntent(Color.BLUE))
        )

        private fun newIntent(color: Int): Function<RevealAnimationSampleActivity, Intent> =
            Function { a -> RevealAnimationShowActivity.createIntent(a, color, a.touchX, a.touchY) }
    }

    private var touchX = 0f
    private var touchY = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val list = findViewById<ListView>(R.id.list)
        list.adapter = ArrayAdapter<Item>(this, android.R.layout.simple_list_item_1, ITEMS)
        list.onItemClickListener = AdapterView.OnItemClickListener { adapterView: AdapterView<*>, view1: View, i: Int, l: Long ->
            val item = adapterView.adapter.getItem(i) as Item
            try {
                startActivity(item.creator.apply(this))
            } catch (e: Exception) {

            }
        }

        val touchOverlay = findViewById<View>(R.id.touch_overlay)
        touchOverlay.setOnTouchListener { view, event ->
            when(event.action) {
                MotionEvent.ACTION_DOWN -> {
                    touchX = event.x
                    touchY = event.y// - getStatusBarHeight()
                }
                else -> {}
            }
            return@setOnTouchListener false
        }
    }

    private fun getStatusBarHeight(): Int {
        val r = Rect()
        window.decorView.getWindowVisibleDisplayFrame(r)
        return r.top
    }
}

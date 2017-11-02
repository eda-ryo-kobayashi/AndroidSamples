package com.eda.androidsamples

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import org.jetbrains.anko.*

class MainActivity : AppCompatActivity() {

    class Item(val label: String, val clazz: Class<out AppCompatActivity>) {
        override fun toString(): String {
            return label
        }
    }

    companion object {
        val ITEMS = arrayOf(
            Item("フォント一覧画面", FontListActivity::class.java),
            Item("カメラキャプチャ", CameraCaptureActivity::class.java),
            Item("カメラキャプチャ(Camera-Kit)", CameraKitActivity::class.java),
            // TODO zxingでQR、Data Matrixキャプチャ
            Item("Chrome Custom TabsでWebページを見る", ChromeViewActivity::class.java),
            Item("加速度センサーを使う", AccelerometerActivity::class.java),
            Item("ジャイロセンサーを使う", GyrosensorActivity::class.java),
            Item("バイブレータを使う", VibratorActivity::class.java),
            Item("フラッシュライトを使う", FlashLightActivity::class.java),
            Item("通知欄にメッセージを表示", NotificationActivity::class.java),
            Item("円グラフ的なやつ表示", CircleGraphActivity::class.java),
            Item("Roomデータベースを使ったテスト", RoomSampleActivity::class.java)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)

//        frameLayout {
//            listView {
//                id = list
//            }
//                .lparams(width = matchParent, height = matchParent)
//        }

        MainActivityUi().setContentView(this)

        val list = findViewById<ListView>(MainActivityUi.list)
        list.adapter = ArrayAdapter<Item>(this, android.R.layout.simple_list_item_1, ITEMS)
        list.onItemClickListener = AdapterView.OnItemClickListener { adapterView: AdapterView<*>, view1: View, i: Int, l: Long ->
            val item = adapterView.adapter.getItem(i) as Item
            try {
                startActivity(Intent(this, item.clazz))
            } catch (e: Exception) {

            }
        }
    }
}

class MainActivityUi : AnkoComponent<MainActivity> {

    companion object {
        val list: Int = 123
    }

    override fun createView(ui: AnkoContext<MainActivity>) = with(ui) {
        frameLayout {
            listView {
                id = list
            }
                .lparams(width = matchParent, height = matchParent)
        }
    }

}
package com.eda.androidsamples

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.eda.androidsamples.palette.ImageSelectionFragment

/**
 * Created by kobayashiryou on 2017/11/02.
 *
 * パレット抽出画面
 */
class PaletteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_palette)

        val ft = supportFragmentManager.beginTransaction()
        ft.add(R.id.content, ImageSelectionFragment.newInstance())
        ft.commit()
    }
}
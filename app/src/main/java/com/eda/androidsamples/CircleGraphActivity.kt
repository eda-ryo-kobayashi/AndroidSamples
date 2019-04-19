package com.eda.androidsamples

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import com.eda.androidsamples.graph.ArcGraphView

/**
 * Created by kobayashiryou on 2017/09/14.
 *
 * 円グラフ
 */
class CircleGraphActivity : AppCompatActivity() {

    private var angleDegree = 180.0f
    private lateinit var cgv: ArcGraphView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_circle_graph)

        cgv = findViewById(R.id.graph)
        cgv.setAngle(angleDegree).update()

        findViewById<View>(R.id.plus).setOnClickListener {
            angleDegree = 225.0f
            cgv.setAngle(0f).animateAngle(225.0f, 1000)
        }
        findViewById<View>(R.id.minus).setOnClickListener {
            angleDegree -= 5.0f
            cgv.setAngle(angleDegree).update()
        }
    }
}
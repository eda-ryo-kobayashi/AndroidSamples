package com.eda.androidsamples

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.eda.androidsamples.transition.TransitionTestFragment

/**
 * Created by kobayashiryou on 2017/11/09.
 *
 * FragmentのScene Transitionについてテストする画面
 */
class SceneTransitionTestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scene_transition_test)

        val ft = supportFragmentManager.beginTransaction()
        ft.add(R.id.content, TransitionTestFragment.newInstance(), "")
        ft.commit()
    }
}
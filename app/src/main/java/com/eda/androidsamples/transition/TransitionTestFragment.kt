package com.eda.androidsamples.transition

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.transition.Scene
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.eda.androidsamples.R
import android.transition.ChangeBounds
import android.transition.TransitionInflater
import android.transition.TransitionManager
import android.util.Log
import org.jetbrains.anko.find


/**
 * Created by kobayashiryou on 2017/11/09.
 *
 * Scene TransitionテストFragment
 */
class TransitionTestFragment : androidx.fragment.app.Fragment() {

    private lateinit var sceneA: Scene
    private lateinit var sceneB: Scene
    private lateinit var sceneC: Scene

    companion object {

        fun newInstance(): TransitionTestFragment {
            return TransitionTestFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_transition_test, container, false) ?: return null

        val sceneRoot = root.find<ViewGroup>(R.id.scene_root)
        sceneA = Scene.getSceneForLayout(sceneRoot, R.layout.view_scene_a, context)
        sceneA.setEnterAction {
            sceneA.sceneRoot.find<View>(R.id.a_btn).setOnClickListener {
                Log.d("Scene A", "click")
            }
        }
        sceneB = Scene.getSceneForLayout(sceneRoot, R.layout.view_scene_b, context)
        sceneB.setEnterAction {
            sceneB.sceneRoot.find<View>(R.id.b_btn).setOnClickListener {
                Log.d("Scene B", "click")
            }
        }
        sceneC = Scene.getSceneForLayout(sceneRoot, R.layout.view_scene_c, context)
        sceneC.setEnterAction {
            sceneC.sceneRoot.find<View>(R.id.c_btn).setOnClickListener {
                Log.d("Scene C", "click")
            }
        }

        root.find<View>(R.id.a).setOnClickListener {
            TransitionManager.go(sceneA, TransitionInflater.from(context).inflateTransition(R.transition.slide_from_left))
        }

        root.find<View>(R.id.b).setOnClickListener {
            TransitionManager.go(sceneB, TransitionInflater.from(context).inflateTransition(R.transition.slide_from_left))
        }

        root.find<View>(R.id.c).setOnClickListener {
            TransitionManager.go(sceneC, ChangeBounds())
        }

        return root
    }
}
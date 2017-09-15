package com.eda.androidsamples

import android.net.Uri
import android.os.Bundle
import android.support.customtabs.CustomTabsIntent
import android.support.customtabs.CustomTabsSession
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.eda.androidsamples.customtabs.CustomTabActivityHelper
import com.eda.androidsamples.customtabs.CustomTabsHelper

/**
 * Created by kobayashiryou on 2017/09/12.
 *
 * ChromeでWeb画面を見るActivity
 */
class ChromeViewActivity : AppCompatActivity(), CustomTabActivityHelper.ConnectionCallback {

    var helper: CustomTabActivityHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chrome_view)

        findViewById<View>(R.id.google).setOnClickListener {
            openUrl("https://google.com", helper?.session)
        }
        findViewById<View>(R.id.yahoo).setOnClickListener {
            openUrl("https://yahoo.co.jp", helper?.session)
        }

        helper = CustomTabActivityHelper()
        helper?.setConnectionCallback(this)
    }

    override fun onStart() {
        super.onStart()
        helper?.bindCustomTabsService(this)
    }

    override fun onStop() {
        super.onStop()
        helper?.unbindCustomTabsService(this)
    }

    private fun openUrl(url: String, session: CustomTabsSession? = null): Unit {
        val intent = CustomTabsIntent.Builder(session)
                .enableUrlBarHiding()
                .setStartAnimations(this, R.anim.slide_in_from_right, R.anim.slide_out_to_left)
                .setExitAnimations(this, R.anim.slide_in_from_left, R.anim.slide_out_to_right)
                .build()
        val uri = Uri.parse(url)
        CustomTabActivityHelper.openCustomTab(this, intent, uri, null)
    }

    private fun setMayLaunch(url: String): Unit {
        val uri = Uri.parse(url)
        if(helper?.mayLaunchUrl(uri, null, null) ?: return) {
            Log.d("Chrome Custom Tabs", url)
        }
    }

    override fun onCustomTabsConnected() {
        setMayLaunch("https://google.com")
        setMayLaunch("https://yahoo.co.jp")
    }

    override fun onCustomTabsDisconnected() {

    }
}
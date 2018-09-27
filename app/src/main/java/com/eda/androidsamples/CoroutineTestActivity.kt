package com.eda.androidsamples

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.experimental.GlobalScope
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.rx2.rxSingle

/**
 * Created by RyoKobayashi on 2018/09/21
 *
 */
class CoroutineTestActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    GlobalScope.launch {
      val s = rxSingle {
        Log.i("CoroutineTestActivity", "not main rxSingle ${Thread.currentThread().name}")
        1
      }
      s.subscribe { a ->
        Log.i("CoroutineTestActivity", "not main subscribe ${Thread.currentThread().name}")
      }

      val x = rxSingle {
        Log.i("CoroutineTestActivity", "main rxSingle ${Thread.currentThread().name}")
        1
      }
          .observeOn(AndroidSchedulers.mainThread())
      x.subscribe { a ->
        Log.i("CoroutineTestActivity", "main subscribe ${Thread.currentThread().name}")
      }
    }

  }
}
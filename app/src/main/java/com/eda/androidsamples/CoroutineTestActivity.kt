package com.eda.androidsamples

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.coroutines.*
import kotlinx.coroutines.rx2.rxSingle
import kotlin.coroutines.CoroutineContext

/**
 * Created by RyoKobayashi on 2018/09/21
 *
 */
class CoroutineTestActivity : AppCompatActivity(), CoroutineScope {

  private var job = Job()
  override val coroutineContext: CoroutineContext
    get() = job + Dispatchers.Main

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

    executeTest()
  }

  override fun onDestroy() {
    super.onDestroy()
    job.cancel()
  }

  private fun executeTest() {
    launch {
      Log.d("start heavy : ", Thread.currentThread().name)
      heavy()
      Log.d("end heavy : ", Thread.currentThread().name)

      Log.d("start heavyIO : ", Thread.currentThread().name)
      heavyIO()
      Log.d("end heavyIO : ", Thread.currentThread().name)
    }
  }

  private suspend fun heavy() {
    delay(1000)
    Log.d("heavy Thread : ", Thread.currentThread().name)
  }

  private fun heavyIO() = launch(Dispatchers.IO) {
    delay(1000)
    Log.d("heavyIO Thread : ", Thread.currentThread().name)
    launch(Dispatchers.Main) {
      Log.d("heavyIO$1 Thread : ", Thread.currentThread().name)
    }
  }
}
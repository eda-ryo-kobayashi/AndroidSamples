package com.eda.androidsamples

import android.os.Build
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

/**
 * Created by RyoKobayashi on 2019-04-19
 *
 */
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.LOLLIPOP])
class ViewModelTest {

  @Test
  fun `ViewModelが生成できるかどうかテスト`() {

  }
}
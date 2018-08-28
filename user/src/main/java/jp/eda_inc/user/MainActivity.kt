package jp.eda_inc.user

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import dagger.android.AndroidInjection
import javax.inject.Inject

// Module化をするときは
// レイヤー・依存・グルーピングをよく考える
class MainActivity : AppCompatActivity() {
//
//  @Inject
//  lateinit var viewModel: MainViewModel

  @Inject
  lateinit var factory: ViewModelProvider.Factory

  lateinit var viewModel: MainViewModel

  override fun onCreate(savedInstanceState: Bundle?) {
    AndroidInjection.inject(this)
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    viewModel = ViewModelProviders.of(this, factory)[MainViewModel::class.java]
  }
}

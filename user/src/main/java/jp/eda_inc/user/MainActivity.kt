package jp.eda_inc.user

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

// Module化をするときは
// レイヤー・依存・グルーピングをよく考える
class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
  }
}

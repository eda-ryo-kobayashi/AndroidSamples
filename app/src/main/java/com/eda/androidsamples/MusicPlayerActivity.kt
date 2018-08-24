package com.eda.androidsamples

import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import android.support.v7.app.AppCompatActivity
import android.view.KeyEvent
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit

/**
 * Created by kobayashiryou on 2018/01/16.
 *
 * ミュージックプレイヤー画面
 */

class MusicPlayerActivity : AppCompatActivity() {

  private lateinit var mediaSession: MediaSessionCompat
  private lateinit var stateBuilder: PlaybackStateCompat.Builder
  private var mp: MediaPlayer? = null
  private var disposable: Disposable? = null
  private lateinit var seekBar: SeekBar
  private lateinit var current: TextView
  private lateinit var time: TextView
  private var isControlling = false

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_music_player)

    // media session作成
    mediaSession = MediaSessionCompat(this, "AS - MusicPlayer")
    mediaSession.setFlags(
        MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS or
            MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS)
    mediaSession.setCallback(object : MediaSessionCompat.Callback() {
      override fun onPlay() {
        setPlaying(true)
      }

      override fun onPause() {
        setPlaying(false)
      }
    },
        Handler(Looper.getMainLooper()))

    mp = MediaPlayer.create(this, R.raw.jazz_in_paris)

    // メディアの更新を検知
    seekBar = findViewById(R.id.seek)
    current = findViewById(R.id.current)
    time = findViewById(R.id.time)
    seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
      override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        if (!fromUser) return
        seekBar?.let { bar ->
          updateUi(bar.progress, bar.max)
        }
      }

      override fun onStartTrackingTouch(p0: SeekBar?) {
        isControlling = true
      }

      override fun onStopTrackingTouch(p0: SeekBar?) {
        isControlling = false
        p0?.let { bar ->
          mp?.let { p ->
            p.seekTo(bar.progress)
            if (p.isPlaying) {
              p.start()
            }
          }
        }
      }
    })

    // 再生・停止
    findViewById<Button>(R.id.play).setOnClickListener {
      togglePlay()
      mediaSession.isActive = true
    }

  }

  override fun onStart() {
    super.onStart()
    startUpdateUi()
  }

  override fun onStop() {
    super.onStop()
    stopUpdateUi()
  }

  override fun onDestroy() {
    super.onDestroy()
    mediaSession.release()
    mp?.release()
    mp = null
    disposable?.dispose()
    disposable = null
  }

  private fun startUpdateUi() {
    disposable = Observable.interval(32, 32, TimeUnit.MILLISECONDS)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe {
          if (isControlling) return@subscribe
          mp?.let { p ->
            updateUi(p.currentPosition, p.duration)
          }
        }
  }

  private fun stopUpdateUi() {
    disposable?.dispose()
    disposable = null
  }

  private fun updateUi(currentPosition: Int, duration: Int) {
    seekBar.progress = currentPosition
    seekBar.max = duration

    val csecond = currentPosition / 1000
    val cminutes = csecond / 60
    val csec = csecond % 60
    current.text = "%02d:%02d".format(cminutes, csec)

    val second = duration / 1000
    val minutes = second / 60
    val sec = second % 60
    time.text = "%02d:%02d".format(minutes, sec)
  }

  private fun setPlaying(play: Boolean) {
    mp?.let { player ->
      val btn = findViewById<Button>(R.id.play)
      if (play) {
        player.start()
        btn.text = "停止"
        updateMediaState(false)
      } else {
        player.pause()
        btn.text = "再生"
        updateMediaState(true)
      }
    }
  }

  private fun togglePlay() {
    mp?.let { player ->
      val btn = findViewById<Button>(R.id.play)
      if (player.isPlaying) {
        player.pause()
        btn.text = "再生"
        updateMediaState(true)
      } else {
        player.start()
        btn.text = "停止"
        updateMediaState(false)
      }
    }
  }

  private fun updateMediaState(isPlaying: Boolean) {
    stateBuilder = PlaybackStateCompat.Builder()
        .setActions(
            PlaybackStateCompat.ACTION_PLAY or
                PlaybackStateCompat.ACTION_PAUSE or
                PlaybackStateCompat.ACTION_PLAY_PAUSE)
        .setState(
            if (isPlaying) PlaybackStateCompat.STATE_PAUSED else PlaybackStateCompat.STATE_PLAYING,
            0,
            1.0f
        )
    mediaSession.setPlaybackState(stateBuilder.build())
  }

  override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      return super.onKeyDown(keyCode, event)
    }
    when (keyCode) {
      KeyEvent.KEYCODE_MEDIA_PLAY -> {
        return true
      }
    }
    return super.onKeyDown(keyCode, event)
  }
}

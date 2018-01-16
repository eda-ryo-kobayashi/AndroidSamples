package com.eda.androidsamples

import android.media.MediaPlayer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
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

    private var mp: MediaPlayer? = null
    private var disposable: Disposable? = null
    private lateinit var seekBar: SeekBar
    private lateinit var current: TextView
    private lateinit var time: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music_player)

        mp = MediaPlayer.create(this, R.raw.jazz_in_paris)

        // メディアの更新を検知
        seekBar = findViewById(R.id.seek)
        current = findViewById(R.id.current)
        time = findViewById(R.id.time)
        disposable = Observable.interval(100, 32, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                mp?.let { p ->
                    seekBar.max = p.duration
                    seekBar.progress = p.currentPosition

                    val csecond = p.currentPosition / 1000
                    val cminutes = csecond / 60
                    val csec = csecond % 60
                    current.text = "%02d:%02d".format(cminutes, csec)

                    val second = p.duration / 1000
                    val minutes = second / 60
                    val sec = second % 60
                    time.text = "%02d:%02d".format(minutes, sec)

                }
            }
        seekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
                p0?.let { bar ->
                    mp?.seekTo(bar.progress)
                    mp?.start()
                }
            }
        })

        findViewById<Button>(R.id.play).setOnClickListener { v ->
            mp?.let { player ->
                val btn = v as Button
                if(player.isPlaying) {
                    player.pause()
                    btn.text = "再生"
                } else {
                    player.start()
                    btn.text = "停止"
                }
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        mp?.release()
        mp = null
        disposable?.dispose()
        disposable = null
    }
}

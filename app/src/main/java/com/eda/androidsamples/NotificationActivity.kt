package com.eda.androidsamples

import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.v4.app.NotificationCompat
import android.support.v4.app.TaskStackBuilder
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.RemoteViews
import kotlin.reflect.KClass

/**
 * Created by kobayashiryou on 2017/09/13.
 *
 * 通知欄に通知を表示する
 */
class NotificationActivity : AppCompatActivity() {

  companion object {
    val CHANNEL_ID_1 = "channel_1"
    val CHANNEL_NAME = "一般的な通知"

    val GREEN = (0xFF shl 24) or (0x4C shl 16) or (0xAF shl 8) or 0x50

    class ButtonListener : BroadcastReceiver() {

      override fun onReceive(p0: Context?, p1: Intent?) {
        Log.i("ButtonListener", "click button!")
      }

    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_notification)

    // Applicationクラスでやるのか？
    createNotificationChannel()

    findViewById<View>(R.id.n1).setOnClickListener {
      val builder = createNotificationBuilder(this, "テスト通知タイトル", "テストです", CHANNEL_ID_1)
      builder.color = GREEN

      // 通知が来た時に開くActivityを設定
      // MainActivityがバックスタックに残っていたら
      // スタック上から起動する
      val pendingIntent = createPendingIntent(this, MainActivity::class, 1024)
      builder.setContentIntent(pendingIntent)

      val notiMgr = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
      notiMgr.notify(1239, builder.build())
    }

    findViewById<View>(R.id.n2).setOnClickListener {
      val builder = NotificationCompat.Builder(this, CHANNEL_ID_1)
          .setSmallIcon(R.drawable.ic_android_white_48dp)
          .setAutoCancel(true)
      val rv = RemoteViews(packageName, R.layout.notification_normal)
      rv.setImageViewResource(R.id.image, R.drawable.mig)
      rv.setTextViewText(R.id.description, "これはテスト通知です")
      builder.setCustomContentView(rv)

      // 開くActivity設定
      val pendingIntent = createPendingIntent(this, MainActivity::class, 1024)
      builder.setContentIntent(pendingIntent)

      // ボタン
      val buttonIntent = Intent(this, ButtonListener::class.java)
      val buttonPendingIntent = PendingIntent.getBroadcast(this, 2048, buttonIntent, 0)

      rv.setOnClickPendingIntent(R.id.update, buttonPendingIntent)

      val notiMgr = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
      notiMgr.notify(1240, builder.build())
    }

    findViewById<View>(R.id.n2a).setOnClickListener {
      val builder = NotificationCompat.Builder(this, CHANNEL_ID_1)
          .setSmallIcon(R.drawable.ic_android_white_48dp)
          .setContentTitle("カスタムレイアウト(大)")
          .setContentText("通知を下にドラッグして確認してください")
          .setAutoCancel(true)
          .setColor(GREEN)
      val rv = RemoteViews(packageName, R.layout.notification_large)
      rv.setImageViewResource(R.id.image, R.drawable.mig)
      rv.setTextViewText(R.id.description, "これは大きなテスト通知です")
      builder.setCustomBigContentView(rv)

      val pendingIntent = createPendingIntent(this, MainActivity::class, 1024)
      builder.setContentIntent(pendingIntent)

      // ボタン
      val buttonIntent = Intent(this, ButtonListener::class.java)
      val buttonPendingIntent = PendingIntent.getBroadcast(this, 2048, buttonIntent, 0)

      rv.setOnClickPendingIntent(R.id.update, buttonPendingIntent)

      val notiMgr = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
      notiMgr.notify(1240, builder.build())
    }

    findViewById<View>(R.id.n2b).setOnClickListener {
      val builder = NotificationCompat.Builder(this, CHANNEL_ID_1)
          .setSmallIcon(R.drawable.ic_android_white_48dp)
          .setContentTitle("カスタムレイアウト(特大)")
          .setContentText("通知を下にドラッグして確認してください")
          .setAutoCancel(true)
          .setColor(GREEN)
      val rv = RemoteViews(packageName, R.layout.notification_huge)
      rv.setImageViewResource(R.id.image, R.drawable.mig)
      rv.setTextViewText(R.id.description, "これはかなり大きなテスト通知です")
      builder.setCustomBigContentView(rv)

      val pendingIntent = createPendingIntent(this, MainActivity::class, 1024)
      builder.setContentIntent(pendingIntent)

      // ボタン
      val buttonIntent = Intent(this, ButtonListener::class.java)
      val buttonPendingIntent = PendingIntent.getBroadcast(this, 2048, buttonIntent, 0)

      rv.setOnClickPendingIntent(R.id.update, buttonPendingIntent)

      val notiMgr = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
      notiMgr.notify(1240, builder.build())
    }

    findViewById<View>(R.id.n3).setOnClickListener {
      val bigStyle = NotificationCompat.BigTextStyle()
      bigStyle.setBigContentTitle("大きい通知のタイトル")
      bigStyle.setSummaryText("なんかの詳細についてなんやかんや")

      var bmp: Bitmap? = null
      try {
        bmp = BitmapFactory.decodeResource(resources, R.drawable.mig)
      } catch (e: Exception) {
        e.printStackTrace()
      }

      bigStyle.bigText("サブタイトル？")

      val builder = NotificationCompat.Builder(this, CHANNEL_ID_1)
          .setSmallIcon(R.drawable.ic_android_white_48dp)
          .setLargeIcon(bmp)
          .setContentTitle("大きい通知の普通のタイトル")
          .setContentText("通知を下にドラッグしてください")
          .setAutoCancel(true)
          .setStyle(bigStyle)
          .setColor(GREEN)

      val pendingIntent = createPendingIntent(this, MainActivity::class, 1024)
      builder.setContentIntent(pendingIntent)

      val notiMgr = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
      notiMgr.notify(1241, builder.build())
    }

    findViewById<View>(R.id.n4).setOnClickListener {
      val bigStyle = NotificationCompat.BigPictureStyle()
      bigStyle.setBigContentTitle("大きい通知のタイトル")
      bigStyle.setSummaryText("大きい通知のテキスト")

      var picture: Bitmap? = null
      var bmp: Bitmap? = null
      try {
        picture = BitmapFactory.decodeResource(resources, R.drawable.puppy)
        bmp = BitmapFactory.decodeResource(resources, R.drawable.mig)
      } catch (e: Exception) {
        e.printStackTrace()
      }

      bigStyle.bigPicture(picture)

      val builder = NotificationCompat.Builder(this, CHANNEL_ID_1)
          .setSmallIcon(R.drawable.ic_android_white_48dp)
          .setLargeIcon(bmp)
          .setContentTitle("大きい通知の普通のタイトル")
          .setContentText("大きい通知の普通のテキスト")
          .setAutoCancel(true)
          .setStyle(bigStyle)
          .setColor(GREEN)
          .setPriority(NotificationCompat.PRIORITY_MAX)

      val pendingIntent = createPendingIntent(this, MainActivity::class, 1024)
      builder
          .setContentIntent(pendingIntent)
          .setFullScreenIntent(pendingIntent, true)

      // アクションを追加
      val actionSetting = Intent(this, SettingActivity::class.java)
      val settingPendingIntent = PendingIntent.getActivity(this, 192, actionSetting, 0)
      builder.addAction(R.drawable.ic_settings_white_24dp, "設定", settingPendingIntent)

      val notiMgr = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
      notiMgr.notify(1241, builder.build())
    }
  }

  private fun createNotificationChannel() {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return
    var channel = NotificationChannel(CHANNEL_ID_1, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT)
    channel.lightColor = Color.GREEN
    channel.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
    val notiMgr = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    notiMgr.createNotificationChannel(channel)
  }

  private fun createNotificationBuilder(context: Context,
                                        title: String,
                                        text: String,
                                        channelId: String): NotificationCompat.Builder {
    return NotificationCompat.Builder(context, channelId)
        .setSmallIcon(R.drawable.ic_android_white_48dp)
        .setContentTitle(title)
        .setContentText(text)
        .setAutoCancel(true)
  }

  private fun <T : Activity> createPendingIntent(packageContext: Context, clazz: KClass<T>, requestCode: Int): PendingIntent {
    val intent = Intent(packageContext, clazz.java)
    val tsBuilder = TaskStackBuilder.create(packageContext)
    tsBuilder.addParentStack(clazz.java)
    tsBuilder.addNextIntent(intent)
    return tsBuilder.getPendingIntent(requestCode, PendingIntent.FLAG_UPDATE_CURRENT)!!
  }

}
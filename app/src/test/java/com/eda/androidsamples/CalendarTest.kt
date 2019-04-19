package com.eda.androidsamples

import android.os.Build
import com.google.common.truth.Truth
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


/**
 * カレンダー処理テスト
 *
 * Created by RyoKobayashi on 2019-04-19
 */
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.M])
class CalendarTest {

  private val theNextDay: Long
    get() {
      val c = Calendar.getInstance()
      c.roll(Calendar.DAY_OF_MONTH, true)
      c.set(Calendar.HOUR_OF_DAY, 0)
      c.set(Calendar.MINUTE, 0)
      c.set(Calendar.SECOND, 0)
      return c.timeInMillis
    }

  @Test
  fun check_0_oclock() {
    val c = Calendar.getInstance()
    c.set(Calendar.HOUR_OF_DAY, 0)
    c.set(Calendar.MINUTE, 0)
    c.set(Calendar.SECOND, 0)

    val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    System.out.print("0 O'Clock : " + sdf.format(c.time))
  }

  @Test
  fun next_day_0_oclock() {
    val c = Calendar.getInstance()
    val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

    System.out.println("today : " + sdf.format(c.time))

    c.roll(Calendar.DAY_OF_MONTH, true)

    c.set(Calendar.HOUR_OF_DAY, 0)
    c.set(Calendar.MINUTE, 0)
    c.set(Calendar.SECOND, 0)

    val theNextDay = Date(theNextDay)
    System.out.println("The next day : " + sdf.format(theNextDay))
  }

  @Test
  fun age_calc_test() {
    val c = Calendar.getInstance()
    val sdf = SimpleDateFormat("yyyy-MM-dd")
    System.out.println("today : " + sdf.format(c.time))

    val age = getAge("1949-05-04")
    println("age : $age")
  }

  @Test
  fun leap_year_test() {
    // 閏年はどうなる
    val sdf = SimpleDateFormat("yyyy-MM-dd")

    val leap = Calendar.getInstance()
    leap.set(Calendar.YEAR, 2000)
    leap.set(Calendar.MONTH, 1)
    leap.set(Calendar.DAY_OF_MONTH, 29)
    System.out.println("2000年 : " + sdf.format(leap.time))

    val c = Calendar.getInstance()
    c.set(Calendar.YEAR, 2001)
    c.set(Calendar.MONTH, 1)
    c.set(Calendar.DAY_OF_MONTH, 29)
    System.out.println("2001年 : " + sdf.format(c.time))

    var age = c.get(Calendar.YEAR) - leap.get(Calendar.YEAR)
    if (c.get(Calendar.DAY_OF_YEAR) < leap.get(Calendar.DAY_OF_YEAR)) {
      age--
    }
    Truth.assertThat(age).isEqualTo(1)
  }

  @Test
  fun day_of_year() {
    // 閏年はどうなる
    val sdf = SimpleDateFormat("yyyy-MM-dd")

    val leap = Calendar.getInstance()
    leap.set(Calendar.YEAR, 2000)
    leap.set(Calendar.MONTH, 11)
    leap.set(Calendar.DAY_OF_MONTH, 31)
    System.out.println("2000年日数 : " + leap.get(Calendar.DAY_OF_YEAR))

    val c = Calendar.getInstance()
    c.set(Calendar.YEAR, 2001)
    c.set(Calendar.MONTH, 11)
    c.set(Calendar.DAY_OF_MONTH, 31)
    System.out.println("2001年日数 : " + c.get(Calendar.DAY_OF_YEAR))

    leap.set(Calendar.YEAR, 2000)
    leap.set(Calendar.MONTH, 1)
    leap.set(Calendar.DAY_OF_MONTH, 29)
    System.out.println("2000年 : " + sdf.format(leap.time))
    c.set(Calendar.YEAR, 2001)
    c.set(Calendar.MONTH, 2)
    c.set(Calendar.DAY_OF_MONTH, 1)
    System.out.println("2001年 : " + sdf.format(c.time))

    Truth.assertThat(leap.get(Calendar.DAY_OF_YEAR)).isEqualTo(c.get(Calendar.DAY_OF_YEAR))
  }

  @Test
  fun leap_year_age_test() {
    // 閏年はどうなる
    val sdf = SimpleDateFormat("yyyy-MM-dd")

    val c = Calendar.getInstance()
    c.set(Calendar.YEAR, 2001)
    c.set(Calendar.MONTH, 1)
    c.set(Calendar.DAY_OF_MONTH, 29)
    System.out.println(sdf.format(c.time))
    val leap = Calendar.getInstance()
    leap.set(Calendar.YEAR, 2000)
    leap.set(Calendar.MONTH, 1)
    leap.set(Calendar.DAY_OF_MONTH, 29)
    val leapString = sdf.format(leap.time)
    println(leapString)

    val age = getAge(c, leapString)
    Truth.assertThat(age).isEqualTo(1)
    println(age.toString() + "歳")
  }

  private fun getAge(birthday: String): Int {
    val today = Calendar.getInstance()
    return getAge(today, birthday)
  }

  private fun getAge(c: Calendar, birthday: String): Int {
    val dayOfBirth = convertDateToCalendar(birthday)
    var age = c.get(Calendar.YEAR) - dayOfBirth.get(Calendar.YEAR)
    val monthNotReach = c.get(Calendar.MONTH) < dayOfBirth.get(Calendar.MONTH)
    val dayNotReach =
      c.get(Calendar.MONTH) <= dayOfBirth.get(Calendar.MONTH) && c.get(Calendar.DAY_OF_MONTH) < dayOfBirth.get(
        Calendar.DAY_OF_MONTH
      )
    if (monthNotReach || dayNotReach) {
      age--
    }
    return age
  }

  private fun convertDateToCalendar(date: String): Calendar {
    val cal = Calendar.getInstance()
    try {
      cal.time = SimpleDateFormat("yyyy-MM-dd").parse(date)
    } catch (e: ParseException) {
      e.printStackTrace()
    }

    return cal
  }

}

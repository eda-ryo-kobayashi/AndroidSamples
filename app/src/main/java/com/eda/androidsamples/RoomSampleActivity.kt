package com.eda.androidsamples

import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.eda.androidsamples.database.SampleDatabase
import com.eda.androidsamples.database.User
import com.eda.androidsamples.databinding.ActivityRoomSampleBinding
import com.eda.androidsamples.databinding.ViewUserItemBinding
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by kobayashiryou on 2017/11/02.
 *
 * Roomの使い方サンプル
 */
class RoomSampleActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRoomSampleBinding
    private lateinit var db: SampleDatabase
    private lateinit var adapter: Adapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_room_sample)

        db = SampleDatabase.create(this, "sample.db")
        // テストユーザーを追加
//        Single.just(User(userId = "1", name = "テストユーザー", age = 20))
//            .map { db.userDao().upsert(it) }
//            .subscribeOn(Schedulers.io())
//            .subscribe()

        // Listアダプター生成
        adapter = Adapter(this)
        binding.list.adapter = adapter
        // 移動・削除コールバック
        val helper = ItemTouchHelper(object: ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView?,
                                viewHolder: RecyclerView.ViewHolder?,
                                target: RecyclerView.ViewHolder?): Boolean {
                if(viewHolder == null || target == null) return false
                val fromPos = viewHolder.adapterPosition
                val toPos = target.adapterPosition
                adapter.notifyItemMoved(fromPos, toPos)
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder?,
                                  direction: Int) {
                if(viewHolder == null) return
                val fromPos = viewHolder.adapterPosition
                Single.just(adapter.removeItem(fromPos))
                    .map { db.userDao().delete(it) }
                    .subscribeOn(Schedulers.io())
                    .subscribe()
            }
        })
        helper.attachToRecyclerView(binding.list)
        binding.list.addItemDecoration(helper)

        // データ取得
        val userDao = db.userDao()
        userDao.getAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                adapter.mergeItems(it)
            }

        binding.addUser.setOnClickListener {
            Single.just(User(userId = System.currentTimeMillis().toString(), name = "テストユーザー", age = 20))
                .map { db.userDao().upsert(it) }
                .subscribeOn(Schedulers.io())
                .subscribe()
        }
    }

    // RecyclerView Adapter
    class Adapter(
        context: Context,
        private val inflater: LayoutInflater = LayoutInflater.from(context),
        private val items: MutableList<User> = mutableListOf()
    ) : RecyclerView.Adapter<ViewHolder>() {

        fun setItems(newItems: List<User>) {
            items.clear()
            items.addAll(newItems)
            notifyDataSetChanged()
        }

        fun mergeItems(incoming: List<User>) {
            var userChanged = false
            val newUsers: MutableList<User> = mutableListOf()
            incoming.forEach {
                val index = getIndexByUserId(it.userId)
                if(index != -1) {
                    items[index] = it
                    userChanged = true
                } else {
                    newUsers.add(it)
                }
            }

            if(!newUsers.isEmpty()) {
                items.addAll(newUsers)
                userChanged = true
            }

            if(userChanged) {
                notifyDataSetChanged()
            }
        }

        fun mergeItem(user: User) {
            val index = getIndexByUserId(user.userId)
            if(index != -1) {
                items[index] = user
                notifyItemChanged(index)
            } else {
                items.add(user)
                notifyItemInserted(index)
            }
        }

        fun removeItem(position: Int): User? {
            var user: User? = null
            if(position != itemCount) {
                user = items.removeAt(position)
                notifyItemRemoved(position)
            }
            return user
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(inflater.inflate(R.layout.view_user_item, parent, false))
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.setItem(items[position])
        }

        override fun getItemCount(): Int {
            return items.size
        }

        private fun getUserById(user: User): User? {
            items.forEach {
                if(it.userId == user.userId) return it
            }
            return null
        }

        private fun getIndexByUserId(userId: String): Int {
            var i = 0
            items.forEach {
                if(it.userId == userId) return i
                i++
            }
            return -1
        }
    }

    // RecyclerView ViewHolder
    class ViewHolder(
        itemView: View,
        private val binding: ViewUserItemBinding? = DataBindingUtil.bind(itemView)
    ): RecyclerView.ViewHolder(itemView) {

        fun setItem(item: User) {
            binding?.item = item
        }
    }
}
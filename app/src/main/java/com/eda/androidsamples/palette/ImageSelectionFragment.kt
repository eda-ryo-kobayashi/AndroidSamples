package com.eda.androidsamples.palette

import android.content.Context
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.eda.androidsamples.R
import com.eda.androidsamples.databinding.FragmentImageSelectionBinding
import com.eda.androidsamples.databinding.ViewImageSelectionItemBinding
import io.reactivex.functions.Consumer

/**
 * Created by kobayashiryou on 2017/11/02.
 *
 * 画像選択画面
 */
class ImageSelectionFragment : androidx.fragment.app.Fragment() {

    private lateinit var binding: FragmentImageSelectionBinding

    companion object {
        fun newInstance(): ImageSelectionFragment {
            return ImageSelectionFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_image_selection, container, false)

        val adapter = Adapter(context!!)
        adapter.callback = Consumer {
            fragmentManager!!.beginTransaction()
                .replace(R.id.content, PaletteDetailFragment.newInstance(it))
                .addToBackStack(null)
                .commit()
        }
        binding.list.adapter = adapter

        return binding.root
    }

    class Adapter(context: Context,
                  private val inflater: LayoutInflater = LayoutInflater.from(context)) : androidx.recyclerview.widget.RecyclerView.Adapter<ViewHolder>() {
        var callback: Consumer<Int>? = null

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            if(position == 0) holder.setItem(R.drawable.puppy)
            else if(position == 1) holder.setItem(R.drawable.mig)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(inflater.inflate(R.layout.view_image_selection_item, parent, false), callback = callback)
        }

        override fun getItemCount(): Int {
            return 2
        }
    }

    class ViewHolder(itemView: View,
                     private val binding: ViewImageSelectionItemBinding? = DataBindingUtil.bind(itemView),
                     private val callback: Consumer<Int>?) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {
        fun setItem(resId: Int) {
            if (binding == null) {
                return
            }
            binding.image.setImageResource(resId)
            binding.image.setOnClickListener {
                try { callback?.accept(resId) } catch (e: Exception) {}
            }
        }
    }
}
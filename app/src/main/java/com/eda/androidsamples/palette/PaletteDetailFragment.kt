package com.eda.androidsamples.palette

import android.databinding.DataBindingUtil
import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.annotation.DrawableRes
import android.support.v4.app.Fragment
import android.support.v7.graphics.Palette
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.eda.androidsamples.R
import com.eda.androidsamples.databinding.FragmentPaletteDetailBinding

/**
 * Created by kobayashiryou on 2017/11/02.
 *
 * パレット詳細画面
 */
class PaletteDetailFragment : Fragment() {

    private lateinit var binding: FragmentPaletteDetailBinding

    companion object {
        private val KEY_RESOURCE_ID = "res_id"

        fun newInstance(@DrawableRes resId: Int): PaletteDetailFragment {
            val f = PaletteDetailFragment()
            val args = Bundle()
            args.putInt(KEY_RESOURCE_ID, resId)
            f.arguments = args
            return f
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_palette_detail, container, false)

        val resId = arguments!!.getInt(KEY_RESOURCE_ID)
        binding.image.setImageResource(resId)
        val bmp = BitmapFactory.decodeResource(resources, resId)
        Palette.from(bmp).generate {
            binding.vibrant.setBackgroundColor(it.getVibrantColor(0x888888))
            binding.vibrantDark.setBackgroundColor(it.getDarkVibrantColor(0x888888))
            binding.vibrantLight.setBackgroundColor(it.getLightVibrantColor(0x888888))
            binding.muted.setBackgroundColor(it.getMutedColor(0x888888))
            binding.mutedDark.setBackgroundColor(it.getDarkMutedColor(0x888888))
            binding.mutedLight.setBackgroundColor(it.getLightMutedColor(0x888888))
        }

        return binding.root
    }
}
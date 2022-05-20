package com.ahmedhamdy.myapplication2.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.ahmedhamdy.myapplication2.R
import com.ahmedhamdy.myapplication2.model.entities.Genre
import com.ahmedhamdy.myapplication2.utils.Constants.BACKDROPIMAGURL
import com.ahmedhamdy.myapplication2.utils.Constants.IMAGURL
import com.bumptech.glide.Glide
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup


@BindingAdapter("imageUrl","isBackdrop", requireAll = true)
fun bindTo(imageView: ImageView?, imagePath: String?, isBackdrop: Boolean){
        if(imagePath != null && imageView != null){
            val baseUrl: String = if (isBackdrop) {
                BACKDROPIMAGURL
            } else {
                IMAGURL
            }
            Glide.with(imageView.context)
                .load(baseUrl + imagePath)
                .placeholder(R.color.md_grey_200)
                .into(imageView)
        }

}


@BindingAdapter("trailerUrl")
fun get(imageView: ImageView?, imageKey: String?){
    if(imageKey != null && imageView != null){
        val thumbnail = "https://img.youtube.com/vi/$imageKey/hqdefault.jpg"
        Glide.with(imageView)
            .load(thumbnail)
            .placeholder(R.color.md_grey_200)
            .into(imageView)
    }
}

@BindingAdapter("items")
fun poster(view: ChipGroup?, generes: List<Genre>?){
    if(view != null && generes != null){
        view.removeAllViews()
        for (genre in generes){
            val chip = Chip(view.context)
            chip.text = genre.name
            view.addView(chip)
        }
    }
}

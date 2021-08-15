package com.kakadurf.randomgifs.presentation.randomgifslist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kakadurf.randomgifs.databinding.LayoutGifItemBinding
import com.kakadurf.randomgifs.presentation.model.GifDto
import pl.droidsonroids.gif.GifDrawable

class GifListAdapter(private val onClickListener: ((GifDto) -> Unit)) : ListAdapter<GifDto, GifHolder>(
    GifDiffCallback
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GifHolder =
        GifHolder.newInstance(parent, onClickListener)

    override fun onBindViewHolder(holder: GifHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private object GifDiffCallback : DiffUtil.ItemCallback<GifDto>() {
        override fun areItemsTheSame(oldItem: GifDto, newItem: GifDto): Boolean {
            return oldItem == newItem
        }
        override fun areContentsTheSame(oldItem: GifDto, newItem: GifDto): Boolean {
            return oldItem.id == newItem.id
        }
    }
}
class GifHolder private constructor(
    private val container: LayoutGifItemBinding,
    private val onClickListener: ((GifDto) -> Unit)
) : RecyclerView.ViewHolder(container.root) {

    companion object {
        fun newInstance(parent: ViewGroup, onClickListener: ((GifDto) -> Unit)): GifHolder {
            val bind = LayoutGifItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return GifHolder(bind, onClickListener)
        }
    }
    fun bind(gifDto: GifDto) {
        container.tvTitle.text = gifDto.title
        if (gifDto.media == null) {
            if (gifDto.isChecked) {
                container.ivSave.setImageDrawable(
                    AppCompatResources.getDrawable(
                        container.root.context,
                        android.R.drawable.btn_star_big_on
                    )
                )
            } else
                container.ivSave.setImageDrawable(AppCompatResources.getDrawable(container.root.context, android.R.drawable.btn_star_big_off))
            container.ivSave.setOnClickListener {
                onClickListener(gifDto)
            }
            Glide.with(container.root.context).asGif().load(
                gifDto.gifUrl
            )
                .placeholder((android.R.drawable.stat_sys_download))
                .into(container.ivGifHolder)
        } else {
            container.ivSave.setImageDrawable(AppCompatResources.getDrawable(container.root.context, android.R.drawable.btn_star_big_on))
            container.ivSave.setOnClickListener {
                onClickListener(gifDto)
                container.ivSave.setImageDrawable(
                    AppCompatResources.getDrawable(
                        container.root.context,
                        android.R.drawable.btn_star_big_off
                    )
                )
            }
            container.ivGifHolder.setImageDrawable(GifDrawable(gifDto.media))
        }
    }
}

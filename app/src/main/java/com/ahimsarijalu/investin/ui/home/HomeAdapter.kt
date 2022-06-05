package com.ahimsarijalu.investin.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.ahimsarijalu.investin.data.datasource.remote.response.DataItem
import com.ahimsarijalu.investin.databinding.ItemPostImageBinding
import com.ahimsarijalu.investin.databinding.ItemRowExploreBinding
import com.bumptech.glide.Glide
import org.imaginativeworld.whynotimagecarousel.listener.CarouselListener
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem
import org.imaginativeworld.whynotimagecarousel.utils.setImage

class HomeAdapter : PagingDataAdapter<DataItem, HomeAdapter.ListViewHolder>(DIFF_CALLBACK) {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class ListViewHolder(private var binding: ItemRowExploreBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: DataItem) {
            Glide.with(itemView.context)
                .load(data.avatar)
                .circleCrop()
                .into(binding.exploreAvatar)
            binding.exploreName.text = data.author
            binding.exploreCategory.text = data.category
            binding.postTv.text = data.text

            val carousel = binding.photoCarousel
            carousel.carouselListener = object : CarouselListener {
                override fun onCreateViewHolder(
                    layoutInflater: LayoutInflater,
                    parent: ViewGroup
                ): ViewBinding {
                    return ItemPostImageBinding.inflate(layoutInflater, parent, false)
                }

                override fun onBindViewHolder(
                    binding: ViewBinding,
                    item: CarouselItem,
                    position: Int
                ) {
                    val currentBinding = binding as ItemPostImageBinding

                    currentBinding.imageView.apply {
                        scaleType = ImageView.ScaleType.CENTER_CROP
                        setImage(item)
                    }
                }
            }
            val photoList = mutableListOf<CarouselItem>()
            if (data.imageUrl != null) {
                for (photo in data.imageUrl) {
                    photoList.add(
                        CarouselItem(
                            imageUrl = photo
                        )
                    )
                }
            }

            carousel.setData(photoList)

            itemView.setOnClickListener {
                onItemClickCallback.onItemClicked(this, data)
            }
        }
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.bind(data)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding =
            ItemRowExploreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    interface OnItemClickCallback {
        fun onItemClicked(view: ListViewHolder, data: DataItem)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DataItem>() {
            override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: DataItem,
                newItem: DataItem
            ): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}
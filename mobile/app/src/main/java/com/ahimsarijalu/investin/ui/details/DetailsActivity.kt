package com.ahimsarijalu.investin.ui.details

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.ahimsarijalu.investin.data.datasource.remote.response.DataItem
import com.ahimsarijalu.investin.databinding.ActivityDetailsBinding
import com.ahimsarijalu.investin.databinding.ItemPostImageBinding
import com.ahimsarijalu.investin.di.Injection
import com.ahimsarijalu.investin.ui.invest.InvestActivity
import com.bumptech.glide.Glide
import kotlinx.serialization.ExperimentalSerializationApi
import org.imaginativeworld.whynotimagecarousel.listener.CarouselListener
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem
import org.imaginativeworld.whynotimagecarousel.utils.setImage

@ExperimentalSerializationApi
class DetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val data = intent.getParcelableExtra<DataItem>(EXTRA_USER)
        if (data != null) {
            setupData(data)
            setupAction(data.authorId)
        }
    }

    private fun setupData(user: DataItem) {
        Glide.with(this@DetailsActivity)
            .load(user.avatar)
            .circleCrop()
            .into(binding.exploreAvatar)
        binding.exploreName.text = user.author
        binding.exploreCategory.text = user.category
        binding.postTv.text = user.text

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
        for (photo in user.imageUrl) {
            photoList.add(
                CarouselItem(
                    imageUrl = photo
                )
            )
        }

        carousel.setData(photoList)
    }

    private fun setupAction(userId: String) {
        binding.exploreAvatar.setOnClickListener {
            moveToProfile(userId)
        }
        binding.exploreName.setOnClickListener {
            moveToProfile(userId)
        }
        binding.exploreCategory.setOnClickListener {
            moveToProfile(userId)
        }
    }

    private fun moveToProfile(userId: String) {
        Intent(this, InvestActivity::class.java).apply {
            putExtra(InvestActivity.EXTRA_UID, userId)
            startActivity(this)
        }
    }

    companion object {
        const val EXTRA_USER = "extra_user"
    }
}
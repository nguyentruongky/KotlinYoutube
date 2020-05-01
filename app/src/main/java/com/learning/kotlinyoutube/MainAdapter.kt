package com.learning.kotlinyoutube

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.video_row.view.*

class MainAdapter(val homeFeed: HomeFeed): RecyclerView.Adapter<CustomViewHolder>() {
    override fun getItemCount(): Int {
        return homeFeed.videos.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val cell = layoutInflater.inflate(R.layout.video_row, parent, false)
        return CustomViewHolder(cell)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val video = homeFeed.videos.get(position)
        holder.itemView.title_textview_video_row.text = video.name
        holder.itemView.author_textview_video_row.text = video.channel.name
        holder.itemView.view_count_textview_video_row.text = video.numberOfViews.toString() + " views"
        Picasso.get().load(video.imageUrl).into(holder.itemView.banner_video_row)
        Picasso.get().load(video.channel.profileImageUrl).into(holder.itemView.avatar_video_row)
        holder.video = video
    }
}

class CustomViewHolder(val view: View, var video: Video? = null): RecyclerView.ViewHolder(view) {
    companion object {
        val VIDEO_TITLE_KEY = "VIDEO_TITLE"
        val COURSE_ID_KEY = "COURSE_ID"
    }
    init {
        view.setOnClickListener {
            val intent = Intent(view.context, CourseDetailActivity::class.java)
            intent.putExtra(VIDEO_TITLE_KEY, video?.name)
            intent.putExtra(COURSE_ID_KEY, video?.id)
            view.context.startActivity(intent)
        }
    }
}
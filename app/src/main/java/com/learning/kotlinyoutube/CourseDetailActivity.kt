package com.learning.kotlinyoutube

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.AdapterListUpdateCallback
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.GsonBuilder
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.course_lesson_row.view.*
import okhttp3.*
import java.io.IOException

class CourseDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerview_main.layoutManager = LinearLayoutManager(this)

        val videoTitle = intent.getStringExtra(CustomViewHolder.VIDEO_TITLE_KEY)
        supportActionBar?.title = videoTitle

        val courseId = intent.getIntExtra(CustomViewHolder.COURSE_ID_KEY, 0)
        if (courseId != 0) {
            getData(courseId.toString())
        }
    }

    fun getData(courseId: String) {
        val url = "https://api.letsbuildthatapp.com/youtube/course_detail?id=$courseId"
        val request = Request.Builder().url(url).build()
        val client = OkHttpClient()
        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string()
                val gson = GsonBuilder().create()
                val lessons = gson.fromJson(body, Array<CourseLesson>::class.java)
                runOnUiThread {
                    recyclerview_main.adapter = CourseDetailAdapter(lessons)
                }
            }

            override fun onFailure(call: Call, e: IOException) {
                print("Fail ${e.toString()}")
            }
        })
    }

    private class CourseDetailAdapter(val lessons: Array<CourseLesson>) : RecyclerView.Adapter<CourseLessonViewHolder>() {
        override fun getItemCount(): Int {
            return lessons.size
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseLessonViewHolder {
            val layoutInflater = LayoutInflater.from(parent?.context)
            val view = layoutInflater.inflate(R.layout.course_lesson_row, parent, false)
            return CourseLessonViewHolder(view)
        }

        override fun onBindViewHolder(holder: CourseLessonViewHolder, position: Int) {
            val lesson = lessons.get(position)
            holder.itemView.title_textview_couse_lesson.text = lesson.name
            holder.itemView.description_textView_course_lesson.text = lesson.link
            holder.itemView.length_textView_course_lesson.text = lesson.duration
            Picasso.get().load(lesson.imageUrl).into(holder.itemView.thumbnail_imageView)
            holder.courseLesson = lesson
        }
    }

    private class CourseLessonViewHolder(val view: View, var courseLesson: CourseLesson? = null) : RecyclerView.ViewHolder(view) {
        init {
            view.setOnClickListener {
                val intent = Intent(view.context, CourseLessonActivity::class.java)
                intent.putExtra("URL", courseLesson?.link)
                view.context.startActivity(intent)
            }
        }
    }
}

class CourseLesson(
    val name: String,
    val duration: String,
    val number: Int,
    val imageUrl: String,
    val link: String
)
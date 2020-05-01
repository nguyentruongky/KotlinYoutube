# Kotlin Youtube
This is the course from [Brian Vong - Lets Build That App](https://www.youtube.com/channel/UCuP2vJ6kRutQBfRmdcI92mA). You can check it out on [Youtube](https://www.youtube.com/watch?v=53BsyxwSBJk&list=PL0dzCUj1L5JGfHj1lwxOq67zAJV3e1S9S). 

### [Episode 1](https://www.youtube.com/watch?v=53BsyxwSBJk&list=PL0dzCUj1L5JGfHj1lwxOq67zAJV3e1S9S&index=1)
- Definition about RecyclerView. Basic implementation. 
```
recyclerview_main.layoutManager = LinearLayoutManager(this)
recyclerview_main.adapter = MainAdapter()
```
- Recycler needs an adapter
```
class MainAdapter: RecyclerView.Adapter<CustomViewHolder>() {
    override fun getItemCount(): Int {
        return 3
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val cell = layoutInflater.inflate(R.layout.video_row, parent, false)
        return CustomViewHolder(cell)
    }
    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.itemView.title_textview_video_row.text = "Hello title label"
    }
}
```

#### Note
- Adapter needs item to render 
```
class CustomViewHolder(v: View): RecyclerView.ViewHolder(v) {}
```
- If we use [Groupie](https://github.com/lisawray/groupie), it's easier.
```
val adapter = GroupAdapter<GroupieViewHolder>()
recyclerview.adapter = adapter
```
- And only define layout to render
```
class UserItem(val user: User) : Item<GroupieViewHolder>() {
	override fun bind(viewHolder: GroupieViewHolder, position: Int) {
		viewHolder.itemView.user_name_textview_new_message.text = user.userName
		Picasso.get().load(user.avatar)
			.into(viewHolder.itemView.avatar_imageView_new_message)
	}

	override fun getLayout(): Int {
		return R.layout.user_row_new_message
	}
}
```
### [Episode 2](https://www.youtube.com/watch?v=53BsyxwSBJk&list=PL0dzCUj1L5JGfHj1lwxOq67zAJV3e1S9S&index=2)
- Call API with [OkHttp](https://square.github.io/okhttp/)
- Install OkHttp: Add `implementation("com.squareup.okhttp3:okhttp:4.6.0")` to app `gradle.build`
- Get request 
```
fun getData() {
    val url = "https://api.letsbuildthatapp.com/youtube/home_feed"
	val request = Request.Builder().url(url).build()
	val client = OkHttpClient()
	client.newCall(request = request).enqueue(object : Callback {
		override fun onResponse(call: Call, response: Response) {
			val body = response.body?.string()
			println(body)
		}
		override fun onFailure(call: Call, e: IOException) {
			println("Fail with $e")
		}
	})
}
```
- Request permission: Open manifest file. 
```
<uses-permission android:name="android.permission.INTERNET"/>
```

- Parse JSON with [Gson](https://guides.codepath.com/android/leveraging-the-gson-library). 
- Install Gson: Add `implementation 'com.google.code.gson:gson:2.8.6'` into app `gradle.build`. 
- Parse. Note: use exactly keys in JSON for data model. (Similar to Codable in Swift). 
```
val gson = GsonBuilder().create()
val homeFeed = gson.fromJson(body, HomeFeed:: class.java)
```
- Run on main thread to update UI.
```
runOnUiThread {
    recyclerview_main.adapter = MainAdapter(homeFeed = homeFeed)
}
```

### [Episode 3](https://www.youtube.com/watch?v=53BsyxwSBJk&list=PL0dzCUj1L5JGfHj1lwxOq67zAJV3e1S9S&index=3)

- Use [Picasso](https://square.github.io/picasso/) to load image
```
Picasso.get().load(video.imageUrl).into(holder.itemView.banner_video_row)
```
- Create circle image view with [CircleImageView](https://github.com/hdodenhof/CircleImageView). 

### [Episode 4](https://www.youtube.com/watch?v=53BsyxwSBJk&list=PL0dzCUj1L5JGfHj1lwxOq67zAJV3e1S9S&index=4)
### [Episode 5](https://www.youtube.com/watch?v=53BsyxwSBJk&list=PL0dzCUj1L5JGfHj1lwxOq67zAJV3e1S9S&index=5)
### [Episode 6](https://www.youtube.com/watch?v=53BsyxwSBJk&list=PL0dzCUj1L5JGfHj1lwxOq67zAJV3e1S9S&index=6)
- Load website in WebView
```
webview.webViewClient = WebViewClient() // without this, it opens Chrome
webview.loadUrl("https://www.github.com")
```

# Conclusion 
Thanks Brian. Your course brings me to a higher level. 

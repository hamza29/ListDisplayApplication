package com.example.listdisplayapplication

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.application.BaseActivity
import com.example.listdisplayapplication.helper.ObjectBox
import com.example.listdisplayapplication.helper.PostModel
import com.google.android.material.snackbar.Snackbar
import io.objectbox.Box
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class MainActivity : BaseActivity() {
    override var layoutRes: Int = R.layout.activity_main
    var PostModeldbBox: Box<PostModel>? = null
    var recyclerView: RecyclerView? = null
    var progressBar: ProgressBar? = null
    var search: EditText? = null
    var recyclerAdapter: CustomAdapter? = null
    var relative: RelativeLayout? = null
    var arraylist: List<PostModel> = ArrayList<PostModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PostModeldbBox = ObjectBox.store.boxFor(PostModel::class.java)
        recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        relative = findViewById<RelativeLayout>(R.id.relative)
        search = findViewById<EditText>(R.id.search)
        progressBar = findViewById<ProgressBar>(R.id.progress)

        if (PostModeldbBox!!.all.size > 0) {
            arraylist = PostModeldbBox!!.all
            Snackbar.make(
                relative!!,
                "Loading data from Local Database",
                Snackbar.LENGTH_SHORT
            ).show()
            setData(arraylist)
        } else {
            progressBar!!.visibility = View.VISIBLE
            Snackbar.make(
                relative!!,
                "Loading data from Api",
                Snackbar.LENGTH_SHORT
            ).show()
            getData()
        }


        search!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun afterTextChanged(editable: Editable) {
                //after the change calling the method and passing the search input
                if (arraylist.size > 0) {
                    filter(editable.toString())
                }
            }
        })
    }

    fun getData() {
        val apiInterface = ApiInterface.create().getPosts()

        //apiInterface.enqueue( Callback<List<Movie>>())
        apiInterface.enqueue(object : Callback<List<PostModel>> {
            override fun onResponse(
                call: Call<List<PostModel>>?,
                response: Response<List<PostModel>>?
            ) {

                if (response?.body() != null) {
                    progressBar!!.visibility = View.GONE
                    PostModeldbBox!!.removeAll()
                    for (i in 0..response.body()!!.size - 1) {
                        val user = PostModel(
                            response.body()!![i].id,
                            response.body()!![i].title,
                            response.body()!![i].body
                        )
                        PostModeldbBox!!.put(user)
                    }
                    arraylist = response.body()!!
                    setData(arraylist)

                }

            }

            override fun onFailure(call: Call<List<PostModel>>?, t: Throwable?) {
                progressBar!!.visibility = View.GONE

            }
        })
    }


    class CustomAdapter(var activity: Activity, posts: List<PostModel>) :
        RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

        var posts: List<PostModel>

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val v: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.rowlayout, parent, false)

            return ViewHolder(v)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.titleTextView.setText("Title: " + posts[position].title)
            holder.descTextView.setText("Body: " + posts[position].body)
            holder.linearLayout.setOnClickListener {
                val intent = Intent(activity, DetailActivity::class.java)
                intent.putExtra("title", posts[position].title)
                intent.putExtra("desc", posts[position].body)
                activity.startActivity(intent)

            }

        }

        override fun getItemCount(): Int {
            return posts.size
        }

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            var titleTextView: TextView
            var descTextView: TextView
            var linearLayout: LinearLayout

            init {
                titleTextView = itemView.findViewById<View>(R.id.title) as TextView
                descTextView = itemView.findViewById<View>(R.id.description) as TextView
                linearLayout = itemView.findViewById<View>(R.id.linear) as LinearLayout

            }
        }


        fun filterList(filterdNames: List<PostModel>) {
            posts = filterdNames
            notifyDataSetChanged()
        }

        init {
            this.posts = posts
        }
    }


    fun setData(posts: List<PostModel>) {
        recyclerAdapter = CustomAdapter(this@MainActivity, posts)
        recyclerView!!.layoutManager = LinearLayoutManager(this@MainActivity)
        recyclerView!!.adapter = recyclerAdapter
    }

    ///////// Filter in list ///////
    private fun filter(text: String) {
        //new array list that will hold the filtered data
        val filterdNames: MutableList<PostModel> =
            ArrayList<PostModel>()

        //looping through existing elements
        for (s in arraylist) {
            //if the existing elements contains the search input
            if (s.title.lowercase().contains(text.lowercase())) {
                //adding the element to filtered list
                filterdNames.add(s)
            }
        }

        //calling a method of the adapter class and passing the filtered list
        recyclerAdapter!!.filterList(filterdNames)
    }


}


package com.example.sis4final

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    private lateinit var progressBar: ProgressBar
    private lateinit var errorText: TextView
    private lateinit var recyclerView: RecyclerView
    private val adapter = PostAdapter()

    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) // XML, не Compose

        progressBar = findViewById(R.id.progressBar)
        errorText = findViewById(R.id.errorText)
        recyclerView = findViewById(R.id.recyclerView)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        errorText.setOnClickListener { loadPosts() }

        loadPosts()
    }

    private fun loadPosts() {
        showLoading()

        uiScope.launch {
            try {
                val posts = withContext(Dispatchers.IO) {
                    Network.api.getPosts()
                }
                adapter.setItems(posts)
                showContent()
            } catch (e: Exception) {
                errorText.text = "Ошибка: ${e.localizedMessage}\nНажмите, чтобы повторить"
                showError()
            }
        }
    }

    private fun showLoading() {
        progressBar.visibility = View.VISIBLE
        errorText.visibility = View.GONE
        recyclerView.visibility = View.GONE
    }

    private fun showError() {
        progressBar.visibility = View.GONE
        errorText.visibility = View.VISIBLE
        recyclerView.visibility = View.GONE
    }

    private fun showContent() {
        progressBar.visibility = View.GONE
        errorText.visibility = View.GONE
        recyclerView.visibility = View.VISIBLE
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}

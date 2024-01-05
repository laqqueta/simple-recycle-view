package com.example.recyleviewexample

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recyleviewexample.databinding.ActivityUserDataRecycleViewBinding

class UserDataRecycleViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserDataRecycleViewBinding
    private lateinit var databaseHandler: DatabaseHandler
    private lateinit var userDataAdapter: UserDataAdapter
    private lateinit var users: MutableList<User>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUserDataRecycleViewBinding
            .inflate(layoutInflater)

        databaseHandler = DatabaseHandler(this)
        setContentView(binding.root)

        setupRecycleView()
    }

    override fun onResume() {
        super.onResume()
        setupRecycleView()
    }

    private fun setupRecycleView() {
        val layoutManager = LinearLayoutManager(this)
        binding.userDataRecycleView.layoutManager = layoutManager

        users = databaseHandler.getAllData()

        Log.i(this.localClassName, "setupRecycleView: $users")

        userDataAdapter = UserDataAdapter(users) {
            //onclick item function
            startActivity(Intent(this, UserDataUpdateActivity::class.java).apply {
                putExtra("user", it)
            })
        }

        binding.userDataRecycleView.adapter = userDataAdapter
    }
}
package com.example.recyleviewexample

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.recyleviewexample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var dbHandler: DatabaseHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        dbHandler = DatabaseHandler(this)

        setContentView(binding.root)

        binding.btnSave.setOnClickListener{
            val user = User(
                username = binding.etUsername.text.toString(),
                address = binding.etAddress.text.toString(),
                phoneNumber = binding.etPhone.text.toString(),
                emailAddress = binding.etEmail.text.toString()
            )

            dbHandler.insert(user)

            if(dbHandler.operationSuccess()) {
                Toast.makeText(this, "Insert user success", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnViewData.setOnClickListener{
            startActivity(Intent(this, UserDataRecycleViewActivity::class.java))
        }
    }
}
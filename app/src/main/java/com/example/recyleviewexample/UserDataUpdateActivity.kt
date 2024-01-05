package com.example.recyleviewexample

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.recyleviewexample.databinding.ActivityUserDataUpdateBinding
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class UserDataUpdateActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserDataUpdateBinding
    private lateinit var db: DatabaseHandler

    @Suppress("DEPRECATION")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUserDataUpdateBinding.inflate(layoutInflater)
        db = DatabaseHandler(this)

        setContentView(binding.root)

        val bundle: Bundle? = intent?.extras
        val data = bundle?.getParcelable<User>("user")!!

        runBlocking {
            launch {
                binding.etUsername.setText(data.username)
                binding.etAddress.setText(data.address)
                binding.etPhone.setText(data.phoneNumber)
                binding.etEmail.setText(data.emailAddress)
            }
        }

        binding.btnUpdate.setOnClickListener {
            db.edit(User(
                id = data.id,
                username = binding.etUsername.text.toString(),
                address = binding.etAddress.text.toString(),
                phoneNumber = binding.etPhone.text.toString(),
                emailAddress = binding.etEmail.text.toString(),
            ))

            if(db.operationSuccess()) {
                Toast.makeText(this, "Update user success", Toast.LENGTH_LONG).show()
            }
        }

        binding.btnDelete.setOnClickListener {
            db.delete(User(
                id = data.id,
                username = binding.etUsername.text.toString(),
                address = binding.etAddress.text.toString(),
                phoneNumber = binding.etPhone.text.toString(),
                emailAddress = binding.etEmail.text.toString(),
            ))

            if(db.operationSuccess()) {
                Toast.makeText(this, "Delete user success", Toast.LENGTH_LONG).show()
            }
        }
    }
}
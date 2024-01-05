package com.example.recyleviewexample

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val id: Long? = null,
    val username: String,
    val address: String,
    val phoneNumber: String,
    val emailAddress: String
) : Parcelable

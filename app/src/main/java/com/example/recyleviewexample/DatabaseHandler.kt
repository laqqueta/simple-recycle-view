package com.example.recyleviewexample

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DatabaseHandler(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){
    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "DB_APP"
        private const val TABLE_USER = "USER"
        private const val KEY_ID = "id"
        private const val KEY_NAME = "name"
        private const val KEY_ADDRESS = "address"
        private const val KEY_PHONE_NUMBER = "phone_number"
        private const val KEY_EMAIL_ADDRESS = "email_address"
    }

    private var queryStatus: Boolean = false

    override fun onCreate(p0: SQLiteDatabase) {
        val createTable = """
            CREATE TABLE $TABLE_USER ($KEY_ID INTEGER PRIMARY KEY, $KEY_NAME TEXT,
            $KEY_ADDRESS TEXT, $KEY_PHONE_NUMBER TEXT, $KEY_EMAIL_ADDRESS TEXT)
        """.trimIndent()

        p0.execSQL(createTable)
    }

    override fun onUpgrade(p0: SQLiteDatabase, p1: Int, p2: Int) {
        p0.execSQL("DROP TABLE IF EXISTS $TABLE_USER")
        onCreate(p0)
    }

    fun insert(userModel: User?) {
        val db = writableDatabase
        try {
            val values = ContentValues()

            values.put(KEY_NAME, userModel?.username)
            values.put(KEY_ADDRESS, userModel?.address)
            values.put(KEY_PHONE_NUMBER, userModel?.phoneNumber)
            values.put(KEY_EMAIL_ADDRESS, userModel?.emailAddress)

            db?.insert(TABLE_USER, null, values)

            Log.i(this.javaClass.simpleName, "insert: data inserted {$values}")
            queryStatus = true
        } catch (e: SQLiteException) {
            Log.e(this.javaClass.simpleName, "insert: ", e.cause)
            queryStatus = false
        } finally {
            db?.close()
        }
    }

    fun getAllData() : MutableList<User> {
        val users: MutableList<User> = ArrayList()

        val db = writableDatabase

        try {
            val cursor: Cursor? = db?.rawQuery("SELECT * FROM $TABLE_USER", null)

            while(cursor!!.moveToNext()) {
                users.add(User(
                    id = cursor.getLong(0),
                    username = cursor.getString(1),
                    address = cursor.getString(2),
                    phoneNumber = cursor.getString(3),
                    emailAddress = cursor.getString(4)
                ))
            }

            cursor.close()
            Log.i(this.javaClass.simpleName, "getAllData: data=${users}")
        } catch (e: SQLiteException) {
            Log.e(this.javaClass.simpleName, "getAllData: ", e.cause)
        } finally {
            db.close()
        }

        return users
    }

    fun delete(user: User) {
        val db = writableDatabase

        try {
            val id: String = user.id.toString()
            db.delete(TABLE_USER, "$KEY_ID = ?", arrayOf(id))
            queryStatus = true
            Log.i(this.javaClass.simpleName, "delete: data deleted {id=$id} ")
        } catch (e: SQLiteException) {
            Log.e(this.javaClass.simpleName, "delete: ", e.cause)
            queryStatus = false
        } finally {
            db.close()
        }

    }

    fun edit(user: User) {
        val db = writableDatabase

        try {
            val values = ContentValues()
            val id: String = user.id.toString()

            values.put(KEY_NAME, user.username)
            values.put(KEY_ADDRESS, user.address)
            values.put(KEY_PHONE_NUMBER, user.phoneNumber)
            values.put(KEY_EMAIL_ADDRESS, user.emailAddress)

            Log.i(this.javaClass.simpleName, "edit: detail_values $values")

            db.update(TABLE_USER, values, "$KEY_ID = ?", arrayOf(user.id.toString()))
            queryStatus = true
            Log.i(this.javaClass.simpleName, "edited: data edited {id=$id} ")
        } catch (e: SQLiteException) {
            Log.e(this.javaClass.simpleName, "edit: ", e.cause)
            queryStatus = false
        } finally {
            db.close()
        }
    }

    fun operationSuccess() : Boolean {
        return queryStatus
    }

}
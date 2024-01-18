package com.example.kr_fedya.db

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.example.kr_fedya.dataclasses.User

class DatabaseManager(context: Context) {
    private val dbHelper = DadabaseHelper(context)
    private var db: SQLiteDatabase? = null

    fun openDb(){
        db = dbHelper.writableDatabase
    }

    fun closeDb(){
        dbHelper.close()
    }

    fun insertUser(name:String, email:String, password:String){
        val values = ContentValues().apply {
            put(Database.COLUMN_USER_NAME, name)
            put(Database.COLUMN_USER_EMAIL, email)
            put(Database.COLUMN_USER_PASSWORD, password)
        }
        db?.insert(Database.TABLE_USERS, null, values);
    }

    @SuppressLint("Range")
    fun getUsers() : ArrayList<User>{
        val usersList = ArrayList<User>()
        val cursor = db?.query(Database.TABLE_USERS, null, null, null, null, null, null)
        with(cursor) {
            while (this?.moveToNext()!!){
                val name = cursor?.getString(cursor.getColumnIndex(Database.COLUMN_USER_NAME))
                val email = cursor?.getString(cursor.getColumnIndex(Database.COLUMN_USER_EMAIL))
                val password = cursor?.getString(cursor.getColumnIndex(Database.COLUMN_USER_PASSWORD))
                if(name.isNullOrEmpty() || email.isNullOrEmpty() || password.isNullOrEmpty()) continue
                val user = User(name, email, password);
                usersList.add(user)
            }
        }
        cursor?.close()
        return usersList
    }

    @SuppressLint("Range")
    fun getUser(email: String, password: String) : User? {
        var user: User? = null
        val query: String = "SELECT * FROM ${Database.TABLE_USERS} WHERE ${Database.COLUMN_USER_EMAIL} = \'$email\' AND ${Database.COLUMN_USER_PASSWORD} = \'$password\'"
        val cursor = db?.rawQuery(query, null)
        with(cursor) {
            while (this?.moveToNext()!!){
                val name = cursor?.getString(cursor.getColumnIndex(Database.COLUMN_USER_NAME))
                val email_ = cursor?.getString(cursor.getColumnIndex(Database.COLUMN_USER_EMAIL))
                val password_ = cursor?.getString(cursor.getColumnIndex(Database.COLUMN_USER_PASSWORD))
                if(name.isNullOrEmpty() || email_.isNullOrEmpty() || password_.isNullOrEmpty()) continue
                user = User(name, email_, password_)
            }
        }
        cursor?.close()
        return user
    }

    fun updateName(name:String, email:String, password:String){
        val cv = ContentValues()
        cv.put(Database.COLUMN_USER_NAME, name)
        cv.put(Database.COLUMN_USER_PASSWORD, password)
        cv.put(Database.COLUMN_USER_EMAIL, email)
        val whereclause = "${Database.COLUMN_USER_EMAIL}=?"
        val whereargs = arrayOf(email)
        db?.update(Database.TABLE_USERS, cv, whereclause, whereargs)
    }

}
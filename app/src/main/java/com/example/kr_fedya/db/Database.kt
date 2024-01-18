package com.example.kr_fedya.db
import android.provider.BaseColumns

object Database {
    const val TABLE_USERS = "user"
    const val COLUMN_USER_NAME = "name"
    const val COLUMN_USER_EMAIL = "email"
    const val COLUMN_USER_PASSWORD = "password"

    const val DATABASE_VERSION = 1
    const val DATABASE_NAME = "ReservationDB.db"

    const val CREATE_TABLE_USER = "CREATE TABLE IF NOT EXISTS $TABLE_USERS (" + "${BaseColumns._ID} INTEGER PRIMARY KEY, $COLUMN_USER_NAME TEXT," +
            "$COLUMN_USER_EMAIL TEXT,$COLUMN_USER_PASSWORD TEXT" + ")"
    const val DROP_TABLE_USER = "DROP TABLE IF EXISTS $TABLE_USERS"
}
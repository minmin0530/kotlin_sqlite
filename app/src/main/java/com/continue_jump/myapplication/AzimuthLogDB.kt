package com.continue_jump.myapplication

import android.content.ContentValues
import android.content.Context
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteDatabase

class AzimuthLogDB(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    companion object {

        private val DB_VERSION = 1
        private val DB_NAME = "azimuthLog.db"

        private val TABLE_NAME = "azimuthLog"
        private val COLUMN_ID = "_id"
        private val COLUMN_TIMESTUMP = "timeStump"
        private val COLUMN_AZIMUTH = "azimuth"

        public val TIME_STUMP = ("TIMESTUMP")
        public val AZIMUTH = ("AZIMUTH")

        private val SQL_CREATE_TABLE = String.format(
            "CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT,%s INTEGER,%s REAL)",
            TABLE_NAME,
            COLUMN_ID,
            COLUMN_TIMESTUMP,
            COLUMN_AZIMUTH
        )

        private val SQL_DROP_TABLE = String.format("DROP TABLE IF EXISTS %s", TABLE_NAME)

        private val SQL_SELECT_BETWEEN_TIMESTUMP =
            String.format("%s BETWEEN ? AND ?", COLUMN_TIMESTUMP)
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onCreate(db)
    }

    fun saveData(timeStump: Long, azimuth: Float) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_TIMESTUMP, timeStump)
        values.put(COLUMN_AZIMUTH, azimuth)
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun loadData(): JSONArray {
        val db = this.readableDatabase
        val cursor = db.query(TABLE_NAME, null, null, null, null, null, null, null)
        val jsonArray = JSONArray()
        try {
            while (cursor.moveToNext()) {
                val jsonObject = JSONObject()
                jsonObject.put(TIME_STUMP, cursor.getLong(1))
                jsonObject.put(AZIMUTH, cursor.getFloat(2))
                jsonArray.put(jsonObject)
            }
        } catch (e: JSONException) {

        } finally {
            db.close()
        }
        return jsonArray
    }

    fun loadData(startMilli: Long, endMilli: Long): JSONArray {

        val db = this.readableDatabase
        val cursor = db.query(
            TABLE_NAME, null, SQL_SELECT_BETWEEN_TIMESTUMP,
            arrayOf(startMilli.toString(), endMilli.toString()), null, null, null, null
        )
        val jsonArray = JSONArray()
        try {
            while (cursor.moveToNext()) {
                val jsonObject = JSONObject()
                jsonObject.put(TIME_STUMP, cursor.getLong(1))
                jsonObject.put(AZIMUTH, cursor.getFloat(2))
                jsonArray.put(jsonObject)
            }
        } catch (e: JSONException) {

        } finally {
            db.close()
        }
        return jsonArray
    }
}
package com.continue_jump.myapplication

import android.graphics.Color
import android.icu.util.GregorianCalendar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.ViewGroup
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    var mSaveAzimuthLog = Runnable {  }
    var mHandler = Handler()
    var mLastAzimuth = AzimuthLogDB(this)
    var mAzimuthLogDB = AzimuthLogDB(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        mSaveAzimuthLog =
//            object : Runnable {
//                override fun run() {
                    val timeStump = GregorianCalendar.getInstance().timeInMillis
//                    outputLogger(timeStump, mLastAzimuth)
                    mAzimuthLogDB.saveData(timeStump, 0.12345f)
//                    mHandler.postDelayed(mSaveAzimuthLog, Util.secToMilli(10))
//                }
//            }
//        mHandler.post(mSaveAzimuthLog)

        val text:TextView = TextView(this)
        val layoutParams: ViewGroup.LayoutParams = ViewGroup.LayoutParams(400, 1000)

        var string = ""
        for (i in 0..mAzimuthLogDB.loadData().length() - 1) {
            string += mAzimuthLogDB.loadData().getJSONObject(i)[AzimuthLogDB.TIME_STUMP].toString() + "\n"
        }
        text.setText( string )
        text.setTextColor(Color.RED)

        addContentView(text, layoutParams)
    }
}

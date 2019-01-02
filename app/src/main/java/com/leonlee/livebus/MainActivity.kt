package com.leonlee.livebus

import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.TextView
import com.leonlee.library.LiveBus

class MainActivity : AppCompatActivity() {

    private lateinit var txtReceive: TextView
    private lateinit var btnStart: Button
    private lateinit var btnPostEvent: Button
    private lateinit var btnPostStickyEvent: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnStart = findViewById(R.id.btn_start)
        btnPostEvent = findViewById(R.id.btn_post_event)
        btnPostStickyEvent = findViewById(R.id.btn_post_sticky_event)
        txtReceive = findViewById(R.id.txt_receive_event)

        btnStart.setOnClickListener { startActivity(Intent(this, Main2Activity::class.java)) }
        btnPostEvent.setOnClickListener { LiveBus.getInstance().with(StringEvent::class.java).setValue(StringEvent("这是一个普通事件 ${System.currentTimeMillis()}")) }
        btnPostStickyEvent.setOnClickListener { LiveBus.getInstance().with(StringEvent::class.java).setValueSticky(StringEvent("这是一个粘性事件 ${System.currentTimeMillis()}")) }

        LiveBus.getInstance().with(StringEvent::class.java).observe(this, Observer { txtReceive.text = it?.message })

    }
}

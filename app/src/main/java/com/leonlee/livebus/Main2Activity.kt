package com.leonlee.livebus

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.TextView
import com.leonlee.library.LiveBus

class Main2Activity : AppCompatActivity() {

    private lateinit var btnPost: Button
    private lateinit var btnRemove: Button
    private lateinit var txtReceive: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        btnPost = findViewById(R.id.btn_post_event)
        btnRemove = findViewById(R.id.btn_remove)
        txtReceive = findViewById(R.id.txt_receive_event_2)

        btnPost.setOnClickListener { LiveBus.getInstance().with(StringEvent::class.java).setValueSticky(StringEvent("这是第二个Activity发出的普通事件")) }
        btnRemove.setOnClickListener { LiveBus.getInstance().with(StringEvent::class.java).removeSticky() }

        LiveBus.getInstance().with(StringEvent::class.java).observe(this, Observer { txtReceive.text = it?.message }, true)

    }
}

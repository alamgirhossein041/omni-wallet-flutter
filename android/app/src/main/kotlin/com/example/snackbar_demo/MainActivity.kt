package com.example.snackbar_demo

import android.content.Context
import android.widget.Toast
import com.intelnet.mylibrary.OmniWallet
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel

class MainActivity: FlutterActivity() {

    private val CHANNEL = "com.example.snackbar_demo"

    private lateinit var channel: MethodChannel

    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)

        channel = MethodChannel(flutterEngine.dartExecutor.binaryMessenger, CHANNEL)



        channel.setMethodCallHandler{ call, result ->

            val arg = call.arguments<Map<String, String>>()
            val message: String? = arg?.get("msg")

            if(call.method=="showtoast"){
//                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                OmniWallet
                    .with(this)
                    .start()
            }

        }
    }
}

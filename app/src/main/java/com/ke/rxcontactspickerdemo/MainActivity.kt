package com.ke.rxcontactspickerdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.ke.rxcontactspicker.RxContactsPicker
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        pick.setOnClickListener {
            RxContactsPicker(this)
                .start().subscribe {
                    AlertDialog.Builder(this)
                        .setTitle("结果")
                        .setMessage(it.toString())
                        .show()
                }
        }


    }
}
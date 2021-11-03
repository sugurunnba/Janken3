package com.example.janken3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.TextKeyListener.clear
import android.view.View
import androidx.core.content.edit
import androidx.preference.PreferenceManager
import com.example.janken3.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        イメージボタンとメソッドの紐付け
        binding.gu.setOnClickListener { onJankenButtonTapped(it)}
        binding.choki.setOnClickListener { onJankenButtonTapped(it) }
        binding.pa.setOnClickListener { onJankenButtonTapped(it) }

//        共有プリファレンスのクリア
        val pref = PreferenceManager.getDefaultSharedPreferences(this)
        pref.edit{
            clear()
        }
    }

    fun onJankenButtonTapped(view: View?){
        val intent = Intent(this, ResultActiviry::class.java)
//        name: 追加したい情報のキー, value: 追加する値
        intent.putExtra("MY_HAND", view?.id)
        startActivity(intent)
    }
}
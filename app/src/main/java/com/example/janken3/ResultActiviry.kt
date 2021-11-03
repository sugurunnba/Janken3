package com.example.janken3

import android.app.Instrumentation
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.preference.PreferenceManager
import com.example.janken3.databinding.ActivityMainBinding
import com.example.janken3.databinding.ActivityResultActiviryBinding

class ResultActiviry : AppCompatActivity() {

    val gu = 0
    val choki = 1
    val pa = 2

    private lateinit var binding: ActivityResultActiviryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultActiviryBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        intent: this.intent, name: 取り出したい情報のキー, defaultValue: データが取り出せなかった時のデフォルト値
        val id = intent.getIntExtra("MY_HAND", 0)

        val myHand: Int
        myHand = when (id) {
            R.id.gu -> {
                binding.myHandImage.setImageResource(R.drawable.gu)
                gu
            }
            R.id.choki -> {
                binding.myHandImage.setImageResource(R.drawable.choki)
                choki
            }
            R.id.pa -> {
                binding.myHandImage.setImageResource(R.drawable.pa)
                pa
            }
            else -> gu
        }


//        コンピュータの手を決める
        val comHand = (Math.random() * 3).toInt()
        when(comHand){
            gu -> binding.comHandImage.setImageResource(R.drawable.com_gu)
            choki -> binding.comHandImage.setImageResource(R.drawable.com_choki)
            pa -> binding.comHandImage.setImageResource(R.drawable.com_pa)
        }

//        勝敗を決める
        val gameResult = (comHand -myHand + 3) % 3
        when(gameResult){
            0 -> binding.resultLabel.setText(R.string.result_draw)  //引き分け
            1 -> binding.resultLabel.setText(R.string.result_win)  //勝った場合
            2 -> binding.resultLabel.setText(R.string.result_lose)  //負けた場合
        }

//        戻る処理
        binding.backButton.setOnClickListener{finish()}

        when(id){
//            setImageResources: ImageViewのコンテンツに画像リソースを指定する
            R.id.gu -> binding.myHandImage.setImageResource(R.drawable.gu)
            R.id.choki -> binding.myHandImage.setImageResource(R.drawable.choki)
            R.id.pa -> binding.myHandImage.setImageResource(R.drawable.pa)
        }
    }


    private fun saveDate(myHand: Int, comHand: Int, gameResult: Int){
//        PreferenceManager.getDefaultSharedPreferences: デフォルトの共有プリファレンスを取得する
        val pref = PreferenceManager.getDefaultSharedPreferences(this)
//        getInt: 共有プリファレンスの設定項目をInt型で取得する
        val gameCount = pref.getInt("GAME_COUNT", 0)
        val winningStreakCount = pref.getInt("WINNING_STREAK_COUNT", 0)
        val lastComHand = pref.getInt("LAST_COM_HAND", 0)
        val lastGameResult = pref.getInt("GAME_RESULT", -1)

        val edtWinningStreakCount: Int =
            when{
                lastGameResult == 2 && gameResult == 2 ->
                    winningStreakCount + 1
                else ->
                    0
            }
//            共有プリファレンスへの書き込みを行う
            val editor = pref.edit()
//            putInt: 共有プリファレンスの設定項目へInt型の値を書き込む, putInt("書き込みたい設定項目名の名称", 書き込む値)
            editor.putInt("GAME_COUNT", gameCount + 1)
                  .putInt("LAST_MY_HAND", comHand)
                  .putInt("LAST_COM_HAND", comHand)
                  .putInt("BEFORE_LAST_COM_HAND", lastComHand)
                  .putInt("GAME_RESULT", gameResult)
//                  書き込んだデータを保存する
                  .apply()
    }
}
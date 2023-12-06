package com.cookandroid.block7

import android.content.Intent
import android.media.Image
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.WindowManager
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import org.w3c.dom.Text
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class MainPage : BaseActivity() {
    private val mysqlConnection = MysqlConnection()
    private val handler = Handler(Looper.getMainLooper())
    private val updateRunnable = object : Runnable {
        override fun run() {

            val customApplication = application as CustomApplication
            val studentId = customApplication.studentId

            timeRefresh()
            updateUserData(studentId)
            handler.postDelayed(this, 1000) // 1초마다 새로고침
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        supportActionBar?.hide()
        setContentView(R.layout.main_page)

        val customApplication = application as CustomApplication
        val studentId = customApplication.studentId


        val layoutManager = LinearLayoutManager(this)
        layoutManager.stackFromEnd = true

        val userid: TextView = findViewById(R.id.user_id)

        val levelprogress: ProgressBar = findViewById(R.id.level_progress)

        levelprogress.visibility = View.VISIBLE
        userid.setText(studentId)

        val mailboxButton: ImageButton = findViewById(R.id.mailbox_button)

        mailboxButton.setOnClickListener {
            val intent = Intent(this, MailDialog::class.java)
            startActivity(intent)

        }



        val battle_button : ImageView = findViewById(R.id.battle_icon)
        battle_button.setOnClickListener{
            val intent = Intent(this, GameActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        handler.post(updateRunnable) // 시작
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(updateRunnable) // 중지
    }

    fun timeRefresh() {
        val currenttime: TextView = findViewById(R.id.current_time)
        val dateFormat = SimpleDateFormat("a hh:mm", Locale.US)
        val currentTimeString = dateFormat.format(Date())
        currenttime.text = currentTimeString
    }

    private fun updateUserData(studentId: String) {
        mysqlConnection.getPlayerByStudentId(studentId) { player ->
            runOnUiThread {

                val username_value = player?.username ?: "No Value found"
                val gem_value = player?.gems
                val gold_value = player?.gold

                val username: TextView = findViewById(R.id.user_name)
                val gem_value_layout: TextView = findViewById(R.id.gem_value)
                val gold_value_layout: TextView = findViewById(R.id.gold_value)

                username.text = username_value
                gem_value_layout.text = gem_value.toString()
                gold_value_layout.text = gold_value.toString()

                val customApplication = application as CustomApplication
                customApplication.user_name = username_value
            }
        }
    }

}

package com.cookandroid.block7

import Mail
import MailAdapter
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MailDialog : BaseActivity() {

    private lateinit var mailRecyclerView: RecyclerView
    private lateinit var mailDetailTextView: TextView
    private lateinit var mailDetailTitleView: TextView
    private lateinit var mailAdapter: MailAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mail_dialog)

        mailRecyclerView = findViewById(R.id.mail_recycler_view)
        mailDetailTextView = findViewById(R.id.mail_detail)
        mailDetailTitleView = findViewById(R.id.mail_detail_title)

        val mails = listOf(
            Mail("냥냥이 월드에 온걸 환영한다옹", "환영한다옹!!!"),
            Mail("제목 1", "안녕하세용. 이건 내용1이에용."),
            Mail("제목 2", "이건 내용2다옹.")
        )

        mailAdapter = MailAdapter(mails) { mail ->
            mailDetailTextView.text = mail.content
            mailDetailTitleView.text = "${mail.title}"
        }

        // 이제 mailAdapter가 초기화된 이후에 호출합니다.
        mailAdapter.deselectAll()
        mailDetailTextView.text = ""
        mailDetailTitleView.text = ""

        mailRecyclerView.layoutManager = LinearLayoutManager(this)
        mailRecyclerView.adapter = mailAdapter

        val mailDialogCloseButton: ImageButton = findViewById(R.id.close_button)
        mailDialogCloseButton.setOnClickListener({
            finish()
        })
    }


}
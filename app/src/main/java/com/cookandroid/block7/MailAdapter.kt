import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cookandroid.block7.R

data class Mail(val title: String, val content: String)

class MailAdapter(private val mails: List<Mail>, private val onMailClick: (Mail) -> Unit) :
    RecyclerView.Adapter<MailAdapter.MailViewHolder>() {

    private var selectedPosition = -1  // 현재 선택된 항목의 위치를 추적합니다.

    inner class MailViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(mail: Mail) {
            val textView = itemView.findViewById<TextView>(R.id.mail_title)
            textView.text = mail.title

            // 선택된 항목의 배경색 및 텍스트 색상을 변경하거나 기본색으로 설정합니다.
            if (adapterPosition == selectedPosition) {
                itemView.setBackgroundColor(Color.WHITE)  // 선택된 배경색
                textView.setTextColor(Color.rgb(35,45,56))
            } else {
                itemView.setBackgroundColor(Color.parseColor("#B7000000"))
                textView.setTextColor(Color.WHITE)  // 기본 텍스트 색상 (예: 검정)
            }

            itemView.setOnClickListener {
                val previousSelected = selectedPosition
                selectedPosition = adapterPosition

                notifyItemChanged(previousSelected)  // 이전 항목의 UI를 업데이트합니다.
                notifyItemChanged(selectedPosition)  // 현재 선택된 항목의 UI를 업데이트합니다.

                onMailClick(mail)
            }
        }

    }

    fun deselectAll() {
        val previousSelected = selectedPosition
        selectedPosition = -1
        notifyItemChanged(previousSelected)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MailViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_mail, parent, false)
        return MailViewHolder(view)
    }

    override fun onBindViewHolder(holder: MailViewHolder, position: Int) {
        holder.bind(mails[position])
    }

    override fun getItemCount(): Int = mails.size


}

package bonch.dev.school.ui.ui


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.persistableBundleOf
import androidx.recyclerview.widget.RecyclerView
import bonch.dev.school.R
import bonch.dev.school.ui.activities.MainAppActivity
import bonch.dev.school.ui.fragments.ChatFragment
import bonch.dev.school.ui.models.Message

class  MessageAdapter(messageList: ArrayList<Message>) : RecyclerView.Adapter<MessageAdapter.MessageHolder>() {

    private var messageList: ArrayList<Message> = if (messageList != null) messageList else ArrayList()

    override fun getItemCount(): Int = messageList.size

    override fun onBindViewHolder(holder: MessageHolder, position: Int) {
        holder.bind(messageList[position])
    }

    open inner class MessageHolder(view: View) : RecyclerView.ViewHolder(view) {
        open fun bind(message: Message) {}
    }

    override fun getItemViewType(position: Int): Int {
        val isUser: Boolean = messageList[position].isUser
        return if (isUser) 1 else 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageHolder {
        return if (viewType == 1) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.user_message_item, parent, false)
            MyMessageViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.other_message_item, parent, false)
            OtherMessageViewHolder(view)
        }
    }

    inner class MyMessageViewHolder (view: View) : MessageHolder(view) {
        private val idTextView = view.findViewById<TextView>(R.id.user_text_message)
        private val idDateView = view.findViewById<TextView>(R.id.user_data_message)

        override fun bind(message: Message) {
            idDateView.text = messageList[position].sentDate
            idTextView.text = messageList[position].messageText
        }
    }

     inner class OtherMessageViewHolder (view: View) : MessageHolder(view) {
        private val idTextView = view.findViewById<TextView>(R.id.other_text_message)
        private val idDateMessage = view.findViewById<TextView>(R.id.other_data_message)
         private val idNameUserMessage = view.findViewById<TextView>(R.id.other_user_name)

        override fun bind(message: Message) {
            idDateMessage.text = messageList[position].sentDate.toString()
            idNameUserMessage.text = "Сергей"
            idTextView.text = messageList[position].messageText
        }
    }

    fun addMessage(message: Message){
        messageList.add(message)
        notifyDataSetChanged()
    }
}



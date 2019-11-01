package bonch.dev.school.ui.fragments

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import bonch.dev.school.R
import bonch.dev.school.ui.models.Message
import bonch.dev.school.ui.ui.MessageAdapter
import java.text.SimpleDateFormat
import android.view.View.OnTouchListener
import kotlinx.android.synthetic.main.fragment_chat.*
import java.util.*
import kotlin.collections.ArrayList


class ChatFragment: Fragment() {

    private lateinit var messageRecyclerView: RecyclerView
    private lateinit var textMessage: EditText
    private lateinit var sendMessageButton: Button

    private var currentIdMessage: Int = 20

    private var messageList: ArrayList<Message> = ArrayList()

     override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_chat, container, false)

        if (savedInstanceState != null) messageList = savedInstanceState!!.getParcelableArrayList<Message>("messageList")!!


        textMessage = view.findViewById(R.id.message_ed)
        sendMessageButton = view.findViewById(R.id.send_message_button)

        sendMessageButton.setOnClickListener {
            if (!textMessage.text.trim().isNullOrEmpty()) sendMessage()
        }

        messageRecyclerView = view.findViewById(R.id.message_recycler_view)
        messageRecyclerView.layoutManager = LinearLayoutManager(container!!.context)
        messageRecyclerView.adapter = MessageAdapter(messageList)

        messageRecyclerView.scrollToPosition(currentIdMessage)

        return view
    }


    fun sendMessage() {

        var messageText: String = textMessage.text.toString()
        val sdf = SimpleDateFormat("dd/MM/yy hh:mm:ss a")
        val messageDate = sdf.format(Date())
        var currentMessage: Message

        currentIdMessage++
        currentMessage = Message(currentIdMessage, messageText, messageDate, true)

        messageList.add(currentMessage)

        (messageRecyclerView.adapter as MessageAdapter).notifyDataSetChanged()

        //(messageRecyclerView.adapter as MessageAdapter).addMessage(currentMessage)

       //  messageRecyclerView.smoothScrollToPosition(currentIdMessage)

        textMessage.text.clear()
    }


    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        savedInstanceState.putParcelableArrayList("messageList", messageList)
    }

//    override fun onViewStateRestored(savedInstanceState: Bundle?) {
//        super.onViewStateRestored(savedInstanceState)
//        messageList = savedInstanceState.getParcelableArrayList<>("messageList")
//    }

}

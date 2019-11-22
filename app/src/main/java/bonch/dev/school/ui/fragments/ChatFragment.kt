package bonch.dev.school.ui.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import bonch.dev.school.R
import bonch.dev.school.ui.models.Message
import bonch.dev.school.ui.ui.MessageAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ChatFragment: Fragment() {

    private lateinit var messageRecyclerView: RecyclerView
    private lateinit var textMessage: EditText
    private lateinit var sendMessageButton: Button

    private var messageList: ArrayList<Message> = ArrayList()
    private var currentIdMessage: Int = 0

    private val childListener = object: ChildEventListener {
        override fun onCancelled(p0: DatabaseError) {
        }

        override fun onChildMoved(p0: DataSnapshot, p1: String?) {
        }

        override fun onChildChanged(p0: DataSnapshot, p1: String?) {
        }

        override fun onChildAdded(p0: DataSnapshot, p1: String?) {
            val message = p0.getValue(Message::class.java)
            if (message !== null) {
                currentIdMessage++
                messageList.add(message)
                messageRecyclerView.adapter!!.notifyDataSetChanged()
                messageRecyclerView.scrollToPosition(messageList.size - 1)
            }
        }

        override fun onChildRemoved(p0: DataSnapshot) {
        }
    }

    private lateinit var mAuth: FirebaseAuth
    private lateinit var mRef: DatabaseReference
    private lateinit var mDatabase: FirebaseDatabase
    private lateinit var user: FirebaseUser

     override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_chat, container, false)

         if (mBundleRecyclerViewState != null) {
             messageList = mBundleRecyclerViewState?.getParcelableArrayList<Message>("messageList")!!
         }

         mAuth = FirebaseAuth.getInstance()
         mDatabase = FirebaseDatabase.getInstance()
         user = mAuth.currentUser!!
         mRef = mDatabase.reference.child("Users").child(user.uid).child("Messages")


         textMessage = view.findViewById(R.id.message_ed)
         sendMessageButton = view.findViewById(R.id.send_message_button)

         messageRecyclerView = view.findViewById(R.id.message_recycler_view)
         messageRecyclerView.layoutManager = LinearLayoutManager(container!!.context)
         messageRecyclerView.adapter = MessageAdapter(messageList)

         messageRecyclerView.scrollToPosition(currentIdMessage - 1)

         if (!isOnline(ChatFragment@context!!)) {
             Toast.makeText(ChatFragment@context, "Проверьте ваше подключение к сети", Toast.LENGTH_SHORT).show()
         }


         return view
    }

    @SuppressLint("SimpleDateFormat")
    private fun sendMessage() {

        val messageText: String = textMessage.text.toString()
        val sdf = SimpleDateFormat("dd/MM/yy hh:mm:ss a")
        val messageDate = sdf.format(Date())
        val currentMessage: Message

        if (isOnline(ChatFragment@context!!)) {
            currentIdMessage++
            currentMessage = Message(currentIdMessage, messageText, messageDate, true)

            mRef.push().setValue(currentMessage)
                .addOnSuccessListener {
                    textMessage.text.clear()
                }

                .addOnFailureListener {
                    Toast.makeText(ChatFragment@ context, "${it.message}", Toast.LENGTH_LONG)
                        .show()
                }
        } else {
            Toast.makeText(ChatFragment@context, "Проверьте ваше подключение к сети", Toast.LENGTH_LONG).show()
        }
    }

    override fun onResume() {
        super.onResume()

        sendMessageButton.setOnClickListener {
            if (textMessage.text.isNotEmpty()) sendMessage()
        }
    }

    override fun onStart() {
        super.onStart()

        mRef.addChildEventListener(childListener)
    }

    override fun onPause() {
        super.onPause()

        mBundleRecyclerViewState = Bundle()
        mBundleRecyclerViewState?.putParcelableArrayList("messageList", messageList)
        messageList.clear()
        mRef.removeEventListener(childListener)
    }

    companion object {
        private var mBundleRecyclerViewState: Bundle? = null
    }

    private fun isOnline(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }
}


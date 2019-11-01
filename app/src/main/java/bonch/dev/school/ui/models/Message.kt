package bonch.dev.school.ui.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.text.SimpleDateFormat
import java.util.*

@Parcelize
data class Message(val messageId: Int, val messageText: String, val sentDate: String, val isUser: Boolean): Parcelable {


    class MessageLab() {

        var messageList: ArrayList<Message> = ArrayList()

        init {
            for (i in 0..20) {
                val sdf = SimpleDateFormat("dd/MM/yy hh:mm:ss a")
                val dateMessage = sdf.format(Date())
                val message: Message
                message = if (i % 2 == 0) Message(
                    i,
                    "Privet test test test test test test test test test #$i",
                    dateMessage,
                    true
                )
                else Message(
                    i,
                    "Hi test test test test test test test test test test test #$i",
                    dateMessage,
                    false
                )
                messageList.add(message)
            }
        }
    }
}
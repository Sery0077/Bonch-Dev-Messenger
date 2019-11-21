package bonch.dev.school.ui.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.text.SimpleDateFormat
import java.util.*

@Parcelize
data class Message(val messageId: Int, val messageText: String, val sentDate: String, val ifUser: Boolean): Parcelable {
    constructor(): this(-1, "a", "", false)
}
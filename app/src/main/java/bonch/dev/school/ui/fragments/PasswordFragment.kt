package layout

import android.net.wifi.hotspot2.pps.Credential
import android.os.Bundle
import android.os.Parcelable
import android.view.*
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import bonch.dev.school.R
import bonch.dev.school.ui.fragments.ProfileFragment
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class PasswordFragment: DialogFragment() {

   private lateinit var changePassBtn: Button

    private lateinit var mAuth: FirebaseAuth
    private lateinit var mRef: DatabaseReference
    private lateinit var mDatabase: FirebaseDatabase
    private lateinit var user: FirebaseUser
    private lateinit var emailUser: String

    private lateinit var fPChangePassBtn: Button
    private lateinit var currentPassEt: EditText
    private lateinit var newPassEt: EditText
    private lateinit var confPassEt: EditText

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.password_fragment, container, false)
        changePassBtn = view.findViewById(R.id.change_password_button)

        fPChangePassBtn = view.findViewById(R.id.change_password_button)
        currentPassEt = view.findViewById(R.id.old_password_edit_text)
        newPassEt = view.findViewById(R.id.new_password_edit_text)
        confPassEt = view.findViewById(R.id.confirm_password_edit_text)

        mDatabase = FirebaseDatabase.getInstance()
        mRef = mDatabase.reference.child("Users")
        mAuth = FirebaseAuth.getInstance()
        user = mAuth.currentUser!!
        emailUser = user.email!!

        changePass()

        return view
    }

    private fun changePass() {
        changePassBtn.setOnClickListener {
            val currentPass = currentPassEt.text.toString()
            val newPass = newPassEt.text.toString()
            val confPass = confPassEt.text.toString()

            val credential = EmailAuthProvider.getCredential(emailUser, currentPass)

            user.reauthenticate(credential)
                .addOnSuccessListener {
                    if (newPass == confPass) {
                        user.updatePassword(newPass)
                            .addOnSuccessListener {
                                Toast.makeText(
                                    ProfileFragment@ context,
                                    "Пароль успешно изменен",
                                    Toast.LENGTH_SHORT
                                ).show()
                                dismiss()
                            }
                            .addOnFailureListener {
                                Toast.makeText(
                                    ProfileFragment@ context,
                                    "${it.message}",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                    } else {
                        Toast.makeText(
                            ProfileFragment@ context,
                            "Пароли не совпадают",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                .addOnFailureListener {
                    Toast.makeText(
                        ProfileFragment@ context,
                        "${it.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
        }
    }
}
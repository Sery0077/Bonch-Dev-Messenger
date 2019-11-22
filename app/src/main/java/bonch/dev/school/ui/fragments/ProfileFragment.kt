package bonch.dev.school.ui.fragments

import android.content.Intent
import android.net.wifi.hotspot2.pps.Credential
import android.os.Bundle
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import bonch.dev.school.R
import bonch.dev.school.ui.activities.MainAppActivity
import bonch.dev.school.ui.activities.SignInActivity
import bonch.dev.school.ui.models.Message
import com.google.firebase.auth.EmailAuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_chat.view.*
import kotlinx.android.synthetic.main.password_fragment.*

class ProfileFragment: Fragment() {

    private lateinit var changePassButton: Button
    private lateinit var signOutBtn: Button
    private lateinit var confirmEmailBtn: Button
    private lateinit var emailUserTw: TextView
    private lateinit var loginUserEt: EditText

    private var loginCurrentUser: String? = null

    private lateinit var mAuth: FirebaseAuth
    private lateinit var mRef: DatabaseReference
    private lateinit var mDatabase: FirebaseDatabase
    private lateinit var user: FirebaseUser

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_profile, container, false)

        changePassButton = root.findViewById(R.id.change_password_button)
        signOutBtn = root.findViewById(R.id.sign_out_button)
        confirmEmailBtn = root.findViewById(R.id.email_confirm_button)
        emailUserTw = root.findViewById(R.id.email_text_view)
        loginUserEt = root.findViewById(R.id.name_edit_text)

        mDatabase = FirebaseDatabase.getInstance()
        mAuth = FirebaseAuth.getInstance()
        user = mAuth.currentUser!!
        user.reload()
        mRef = mDatabase!!.reference.child("Users").child(user.uid)

        mRef.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(p0: DataSnapshot) {
                val login = p0.child("login").getValue(String::class.java)
                if (login !== null) {
                    loginUserEt.setText(login)
                }
            }

        })

        emailUserTw.text = user.email

        if (loginCurrentUser !== null) loginUserEt.setText(loginCurrentUser)

        if (user.isEmailVerified) {
            confirmEmailBtn.isVisible = false
        }

        changePassword()
        signOut()
        confirmEmail()

        return root
    }

   private fun changePassword() {

       changePassButton.setOnClickListener() {
           (context as MainAppActivity).changePasswordFragment()
       }
    }

   private fun signOut() {
       signOutBtn.setOnClickListener {
           mAuth.signOut()

           val intent = Intent(MainAppActivity@context, SignInActivity::class.java)
           intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
           startActivity(intent)
       }
    }

    private fun confirmEmail() {
        confirmEmailBtn.setOnClickListener {
            user.sendEmailVerification()
                .addOnSuccessListener {
                    Toast.makeText(ProfileFragment@context, "Письмо с подтвержденим отправлено на почту", Toast.LENGTH_LONG).show()
                }
                .addOnFailureListener {
                    Toast.makeText(ProfileFragment@context, "${it.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }

}
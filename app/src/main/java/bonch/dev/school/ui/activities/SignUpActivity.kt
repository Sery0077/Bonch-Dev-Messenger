package bonch.dev.school.ui.activities

import android.content.Intent
import android.icu.util.EthiopicCalendar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import bonch.dev.school.R
import bonch.dev.school.R.id.complete_sign_up_button
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.sign_up_activity.*
import java.io.IOError
import java.io.IOException
import java.lang.Exception

class SignUpActivity : AppCompatActivity() {

    private lateinit var signUpButton: Button
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mRef: DatabaseReference
    private lateinit var mDatabase: FirebaseDatabase
    private lateinit var dataSnapshot: DataSnapshot

    private lateinit var loginEt: EditText
    private lateinit var emailEt: EditText
    private lateinit var passEt: EditText
    private lateinit var cPassEt: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sign_up_activity)

        loginEt = findViewById(R.id.login_et)
        emailEt = findViewById(R.id.email_et)
        passEt = findViewById(R.id.password_sign_up_et)
        cPassEt = findViewById(R.id.password_confirm_et)
        signUpButton = findViewById(R.id.complete_sign_up_button)

        mDatabase = FirebaseDatabase.getInstance()
        mRef = mDatabase!!.reference.child("Users")
        mAuth = FirebaseAuth.getInstance()

        signUpButton.setOnClickListener { signUp() }

    }

    class FbLocalizedMessage(): Throwable() {
        override fun getLocalizedMessage(): String? {

            return super.getLocalizedMessage()
        }
    }

    private fun signUp() {

        var login = loginEt.text.toString()
        var email = emailEt.text.toString()
        var password = passEt.text.toString()
        var cPassword = cPassEt.text.toString()

        if (password == cPassword && password.isNotEmpty()) {
            mAuth
                .createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                        val user = mAuth!!.currentUser!!.uid
                        val currentUserDb = mRef!!.child(user)
                        currentUserDb.child("name").setValue(login)

                        mainAppActivity()
                }
//                .addOnFailureListener(this) {
//                    Toast.makeText(this, "$", Toast.LENGTH_LONG).show()
//                }
                .addOnFailureListener { FirebaseAuthException ->
                    Toast.makeText(this, "${FirebaseAuthException.localizedMessage}", Toast.LENGTH_LONG).show()
                }
        } else Toast.makeText(this, "Пароли должны совпадать и быть больше 6 символов", Toast.LENGTH_LONG).show()
    }

    private fun mainAppActivity() {
        val intent = Intent(this, MainAppActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

}

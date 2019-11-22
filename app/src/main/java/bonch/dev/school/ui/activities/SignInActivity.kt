package bonch.dev.school.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import bonch.dev.school.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.sign_in_activity.*

class SignInActivity : AppCompatActivity() {

    private lateinit var signUpButton: Button
    private lateinit var signInButton: Button
    private lateinit var emailEt: EditText
    private lateinit var passwordEt: EditText

    private lateinit var mAuth: FirebaseAuth
    private lateinit var mRef: DatabaseReference
    private lateinit var mDatabase: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sign_in_activity)

        mDatabase = FirebaseDatabase.getInstance()
        mRef = mDatabase.reference.child("Users")
        mAuth = FirebaseAuth.getInstance()

        emailEt = findViewById(R.id.email_sign_in_edit_text)
        passwordEt = findViewById(R.id.password_sign_in_edit_text)
        signUpButton = sign_up_button
        signInButton = sign_in_button


        signUpActivity()
        signIn()

    }

    private fun signUpActivity() {
        signUpButton.setOnClickListener {
            val intent = Intent(this, SignUpActivity()::class.java)
            startActivity(intent)
        }
    }

    private fun signIn() {
        signInButton.setOnClickListener {
            val email = emailEt.text.toString()
            val password = passwordEt.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                mAuth.signInWithEmailAndPassword(email, password)
                    .addOnSuccessListener {

                        val intent = Intent(this, MainAppActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "${it.message}", Toast.LENGTH_LONG).show()
                    }
            } else Toast.makeText(this, "Введите почту и пароль", Toast.LENGTH_LONG).show()
        }
    }
}

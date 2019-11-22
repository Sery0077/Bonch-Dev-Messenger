package bonch.dev.school.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import bonch.dev.school.R
import bonch.dev.school.ui.models.User
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class SignUpActivity : AppCompatActivity() {

    private lateinit var signUpButton: Button
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mRef: DatabaseReference
    private lateinit var mDatabase: FirebaseDatabase

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
        mAuth = FirebaseAuth.getInstance()
        mRef = mDatabase.reference.child("Users")

        signUpButton.setOnClickListener {
            signUp()
        }
    }

    private fun signUp() {

        val login = loginEt.text.toString()
        val email = emailEt.text.toString()
        val password = passEt.text.toString()
        val cPassword = cPassEt.text.toString()

        if (password == cPassword && password.isNotEmpty() && login.isNotEmpty()) {
            mAuth
                .createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(object : OnCompleteListener<AuthResult> {
                    override fun onComplete(task: Task<AuthResult>) {
                        if (task.isSuccessful) {
                            val user = User(email, login)
                            val uid = mAuth.currentUser!!.uid

                            mRef.child(uid).setValue(user)
                                .addOnCompleteListener(object : OnCompleteListener<Void> {
                                    override fun onComplete(task: Task<Void>) {
                                        if (task.isSuccessful) {
                                            mainAppActivity()
                                        }
                                    }
                                })
                                .addOnFailureListener {
                                    Toast.makeText(
                                        this@SignUpActivity,
                                        it.localizedMessage,
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                        }
                    }
                })
                .addOnFailureListener {
                    Toast.makeText(this@SignUpActivity, it.localizedMessage, Toast.LENGTH_LONG)
                        .show()
                }
        } else
            if (cPassword !== password) Toast.makeText(
                this,
                "Пароли должны совпадать и быть больше 6 символов",
                Toast.LENGTH_LONG
            ).show()
            else Toast.makeText(
                this,
                "Все поля должны быть заполнены",
                Toast.LENGTH_LONG
            ).show()

    }

    private fun mainAppActivity() {
        val intent = Intent(this, MainAppActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

}

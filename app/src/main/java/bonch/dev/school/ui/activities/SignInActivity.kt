package bonch.dev.school.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import bonch.dev.school.R
import kotlinx.android.synthetic.main.sign_in_activity.*

class SignInActivity : AppCompatActivity() {

    private lateinit var signUpButton: Button
    private lateinit var signInButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sign_in_activity)

        signUpButton = sign_up_button
        signInButton = sign_in_button
        signUpActivity()
        mainActivty()

    }

    fun signUpActivity() {
        signUpButton.setOnClickListener() {
            val intent = Intent(SignInActivity@ this, SignUpActivity()::class.java)
            startActivity(intent)
        }
    }

    fun mainActivty() {
        signInButton.setOnClickListener() {
            val intent = Intent(SignInActivity@ this, MainAppActivity()::class.java)
            startActivity(intent)
        }
    }

}

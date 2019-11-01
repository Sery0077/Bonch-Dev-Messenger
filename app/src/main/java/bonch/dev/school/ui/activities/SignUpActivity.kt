package bonch.dev.school.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import bonch.dev.school.R
import bonch.dev.school.R.id.complete_sign_up_button

class SignUpActivity : AppCompatActivity() {

    private lateinit var signUpButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sign_up_activity)
        signUpButton = findViewById(complete_sign_up_button)
        signUp()
    }

    private fun signUp() {
        signUpButton.setOnClickListener() {
            val intent = Intent(SignUpActivity@ this, MainAppActivity::class.java)
            startActivity(intent)
        }
    }
}

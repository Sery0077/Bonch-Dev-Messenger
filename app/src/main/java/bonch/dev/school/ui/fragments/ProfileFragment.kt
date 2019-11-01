package bonch.dev.school.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import bonch.dev.school.R
import bonch.dev.school.ui.activities.MainAppActivity
import bonch.dev.school.ui.models.Message

class ProfileFragment: Fragment() {

    private lateinit var changePassButton: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_profile, container, false)
        changePassButton = root.findViewById(R.id.change_password_button)
        changePassword()

        return root
    }

   private fun changePassword() {
        changePassButton.setOnClickListener() {
            (context as MainAppActivity).changePasswordFragment()
        }
    }

}
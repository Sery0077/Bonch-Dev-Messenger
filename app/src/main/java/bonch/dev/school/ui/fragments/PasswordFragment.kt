package layout

import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import bonch.dev.school.R

class PasswordFragment: DialogFragment() {

   // private lateinit var button: Button

    private lateinit var fragmentView: WindowManager
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.password_fragment, container, false)
    }
}
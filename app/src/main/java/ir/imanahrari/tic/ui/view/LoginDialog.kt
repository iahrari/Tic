package ir.imanahrari.tic.ui.view

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import ir.imanahrari.tic.R
import ir.imanahrari.tic.service.utilities.setUser
import kotlinx.android.synthetic.main.login_dialog.*

class LoginDialog(private val activity: MainActivity): DialogFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.login_dialog, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        confirm.setOnClickListener { onClick() }

        pass.setOnKeyListener { _, keyCode, event ->
            if(event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER)
                onClick()

            true
        }

        dialog!!.setTitle("ورود")
    }

    private fun onClick(){
        when {
            user.text.toString() == "" -> user.error = "این فیلد نمی تواند خالی باشد!"
            pass.text.toString() == "" -> pass.error = "این فیلد نمی تواند خالی باشد!"
            else -> login()
        }
    }

    private fun login(){
        activity.setUser(user.text.toString(), pass.text.toString())
        activity.viewModel.setContext(context!!)
        dismiss()
    }
}
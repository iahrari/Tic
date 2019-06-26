package ir.imanahrari.tic.ui.view

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import ir.imanahrari.tic.R
import ir.imanahrari.tic.service.utilities.setUser

class LoginDialog: DialogFragment() {

    lateinit var binding: ir.imanahrari.tic.databinding.LoginDialogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.AppTheme)
        isCancelable = false
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.login_dialog, container, false)
        binding.confirm.setOnClickListener { onClick() }
        binding.exit.setOnClickListener { activity!!.onBackPressed() }
        binding.pass.setOnKeyListener { _, keyCode, event ->
            if(event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER)
                onClick()
            true
        }

        return binding.root
    }

    private fun onClick(){
        when {
            binding.user.text.toString() == "" -> binding.user.error = "این فیلد نمی تواند خالی باشد!"
            binding.pass.text.toString() == "" -> binding.pass.error = "این فیلد نمی تواند خالی باشد!"
            else -> login()
        }
    }

    private fun login(){
        (activity as MainActivity).setUser(binding.user.text.toString(), binding.pass.text.toString())
        (activity as MainActivity).viewModel.setContext(context!!)
        dismiss()
    }
}
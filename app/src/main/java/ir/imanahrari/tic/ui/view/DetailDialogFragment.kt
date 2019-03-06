package ir.imanahrari.tic.ui.view

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import ir.imanahrari.tic.R
import ir.imanahrari.tic.service.model.LessonModel

class DetailDialogFragment(val data: LessonModel): DialogFragment() {
    lateinit var binding: ir.imanahrari.tic.databinding.DetailLayoutBinding
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.detail_layout, null, false)

        binding.data = data
        binding.list = readyList()

        return AlertDialog.Builder(context!!, R.style.AppTheme).setView(binding.root).create()
    }

    private fun readyList(): String{
        var list = ""
        for(d in data.getAbsentsL())
            list += "$d\n"

        return list
    }
}
package ir.imanahrari.tic.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment

import ir.imanahrari.tic.R
import ir.imanahrari.tic.databinding.DetailLayoutBinding
import ir.imanahrari.tic.service.model.LessonModel

class DetailDialogFragment(val data: LessonModel): DialogFragment() {
    lateinit var binding: DetailLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.AppTheme)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.detail_layout, container, false)
        binding.toolbar.setNavigationOnClickListener { dismiss() }

        binding.data = data
        binding.list = readyList(data)
        return binding.root
    }

    private fun readyList(data: LessonModel): String{
        var list = ""
        for(d in data.getAbsentsL())
            list += "$d\n"

        return list
    }
}
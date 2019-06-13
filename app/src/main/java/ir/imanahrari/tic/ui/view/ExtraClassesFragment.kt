package ir.imanahrari.tic.ui.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil

import ir.imanahrari.tic.R
import ir.imanahrari.tic.databinding.FragmentExtraClassesBinding
import ir.imanahrari.tic.service.model.ClassModel
import ir.imanahrari.tic.service.model.LessonModel

class ExtraClassesFragment : Fragment() {
    lateinit var binding: FragmentExtraClassesBinding
//    private var data: List<ClassModel>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.fragment_extra_classes, null, false)
        Log.i("logging", "on create fragment")
        return binding.root.apply{ rotationY = 180f }
    }

    fun setData(data: List<ClassModel>){
        Log.i("logging", "set data")
        binding.data = data
    }
}

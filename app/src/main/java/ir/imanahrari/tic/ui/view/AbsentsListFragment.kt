package ir.imanahrari.tic.ui.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil

import ir.imanahrari.tic.R
import ir.imanahrari.tic.databinding.FragmentAbsentsListBinding
import ir.imanahrari.tic.service.model.DataModel

class AbsentsListFragment: Fragment() {
    private var data: DataModel? = null
    private lateinit var binding: FragmentAbsentsListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.fragment_absents_list, null, false)

        return binding.root.apply{ rotationY = 180f }
    }

    fun setData(data: DataModel){
        this.data = data
        binding.data = data
    }
}

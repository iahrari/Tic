package ir.imanahrari.tic.ui.adapter

import android.util.Log
import android.view.View
import android.widget.Toast

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ir.imanahrari.tic.service.model.ClassModel

import ir.imanahrari.tic.service.model.DataModel
import ir.imanahrari.tic.ui.view.DetailDialogFragment

object BindingAdapters {
    @JvmStatic
    @BindingAdapter("setOnClickD")
    fun setOnClickD(view: View, dataL: DataModel) {
        view.setOnClickListener {
            DetailDialogFragment(dataL.data[0]).show(
                dataL.activity.supportFragmentManager,
                "detail"
            )
        }
    }

    @JvmStatic
    @BindingAdapter("setRecyclerViewUtil")
    fun setRecycler(view: RecyclerView, data: DataModel?) {
        view.layoutManager = LinearLayoutManager(view.context)
        view.itemAnimator = DefaultItemAnimator()
        if(data != null)
            view.adapter = MainAdapter(data)
    }

    @JvmStatic
    @BindingAdapter("setClassRecycle")
    fun setClassRecycler(v: RecyclerView, data: List<ClassModel>?){
        v.layoutManager = LinearLayoutManager(v.context)
        v.itemAnimator = DefaultItemAnimator()
        if(data != null)
            v.adapter = ClassAdapter(data)
    }
}
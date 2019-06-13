package ir.imanahrari.tic.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import ir.imanahrari.tic.R
import ir.imanahrari.tic.databinding.ExtraClassRowBinding
import ir.imanahrari.tic.service.model.ClassModel

class ClassAdapter(val data: List<ClassModel>): RecyclerView.Adapter<ClassAdapter.MViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MViewHolder =
        MViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.extra_class_row,
                parent,
                false
            )
        )


    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(h: MViewHolder, p: Int) {
        h.v.data = data[p]
    }


    inner class MViewHolder(val v: ExtraClassRowBinding): RecyclerView.ViewHolder(v.root)
}
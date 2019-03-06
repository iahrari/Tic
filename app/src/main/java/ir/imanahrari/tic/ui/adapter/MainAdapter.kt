package ir.imanahrari.tic.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import ir.imanahrari.tic.R
import ir.imanahrari.tic.service.model.LessonModel
import ir.imanahrari.tic.databinding.RowLayoutBinding
import ir.imanahrari.tic.service.model.DataModel

class MainAdapter(private val dataL: DataModel): RecyclerView.Adapter<MainAdapter.ViewHolder>() {
    val data = dataL.data
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.row_layout, parent, false))
    }

    override fun getItemCount(): Int { return data.size }

    override fun onBindViewHolder(h: ViewHolder, position: Int) {
        val d: MutableList<LessonModel> = ArrayList()
        d.add(data[position])
        h.view.data = DataModel(d, dataL.activity)
    }

    inner class ViewHolder(val view: RowLayoutBinding): RecyclerView.ViewHolder(view.root)
}
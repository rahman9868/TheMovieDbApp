package com.example.core.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.core.R
import com.example.core.databinding.ItemListPosterBinding
import com.example.core.domain.model.Movie

class MoviePosterAdapter : RecyclerView.Adapter<MoviePosterAdapter.ListViewHolder>() {

    private var listData = ArrayList<Movie>()
    var onItemClick: ((Movie) -> Unit)? = null
    private val BASE_IMAGE_URL = "https://image.tmdb.org/t/p/w500"

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newListData: List<Movie>?) {
        if (newListData == null) return
        listData.clear()
        listData.addAll(newListData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_list_poster, parent, false))

    override fun getItemCount() = listData.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = listData[position]
        holder.bind(data)
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemListPosterBinding .bind(itemView)
        fun bind(data: Movie) {
            with(binding) {
                Glide.with(itemView.context)
                    .load("$BASE_IMAGE_URL${data.posterPath}")
                    .into(ivImagePoster)

                binding.root.setOnClickListener {
                    onItemClick?.invoke(data)
                }
            }
        }
    }
}
package com.rolandoselvera.founditall.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.rolandoselvera.founditall.R
import com.rolandoselvera.founditall.databinding.ItemListResultsBinding
import com.rolandoselvera.founditall.data.model.ResultModel

class SearchAdapter(private val onItemClicked: (ResultModel) -> Unit) :
    ListAdapter<ResultModel, SearchAdapter.SearchViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        return SearchViewHolder(
            ItemListResultsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val current = getItem(position)

        holder.itemView.setOnClickListener {
            onItemClicked(current)
        }

        holder.bind(current)
    }

    inner class SearchViewHolder(private var binding: ItemListResultsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(resultModel: ResultModel) {
            binding.apply {
                title.text = resultModel.name
                category.text = resultModel.type?.replaceFirstChar {  // Convierte el primer caracter del texto a mayúscula
                    it.uppercase()
                }

                // Muestra miniaturas (thumbnails) de videos de YouTube con Glide:
                Glide.with(image.context)
                    .asBitmap()
                    .error(R.drawable.ic_img_preview)  // Miniatura si ocurre un error al cargar imagen
                    .load(
                        image.context.getString(
                            R.string.youtube_url_thumbnail, resultModel.youTubeId
                        )
                    )
                    .diskCacheStrategy(DiskCacheStrategy.DATA)
                    .into(image)
            }
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<ResultModel>() {
            override fun areItemsTheSame(oldItem: ResultModel, newItem: ResultModel): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: ResultModel, newItem: ResultModel): Boolean {
                return oldItem.name == newItem.name
            }

        }
    }
}
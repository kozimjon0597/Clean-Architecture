package uz.kozimjon.cleanarchitecture.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import uz.kozimjon.cleanarchitecture.R
import uz.kozimjon.cleanarchitecture.databinding.AdapterFavouriteBinding
import uz.kozimjon.cleanarchitecture.presentation.model.Favourite


class FavouriteAdapter(private val listener: OnFavouriteClickListener)
    : ListAdapter<Favourite, FavouriteAdapter.FavouriteViewHolder>(FavouriteDiffUtil()) {

    interface OnFavouriteClickListener {
        fun onFavouriteClick(favourite: Favourite)
        fun onFavouriteDeleteClick(favourite: Favourite)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteViewHolder {
        return FavouriteViewHolder(AdapterFavouriteBinding
            .inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: FavouriteViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    // ViewHolder
    inner class FavouriteViewHolder(val binding: AdapterFavouriteBinding): RecyclerView.ViewHolder(binding.root) {
        fun onBind(item: Favourite) {
            Glide.with(itemView.context).load(item.image).centerCrop().placeholder(R.drawable.ic_launcher_background).into(binding.ivImage)
            binding.tvName.text = item.name ?: "No name"
            binding.tvPrice.text = "${item.net_price} $"

            itemView.setOnClickListener {
                listener.onFavouriteClick(item)
            }

            binding.ivRemove.setOnClickListener {
                listener.onFavouriteDeleteClick(item)
            }
        }
    }

    // DiffUtil
    class FavouriteDiffUtil : DiffUtil.ItemCallback<Favourite>() {
        override fun areItemsTheSame(oldItem: Favourite, newItem: Favourite): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Favourite, newItem: Favourite): Boolean {
            return oldItem == newItem
        }
    }
}
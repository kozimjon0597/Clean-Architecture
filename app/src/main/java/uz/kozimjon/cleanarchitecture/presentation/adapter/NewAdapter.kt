package uz.kozimjon.cleanarchitecture.presentation.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import uz.kozimjon.cleanarchitecture.R
import uz.kozimjon.cleanarchitecture.databinding.AdapterCategoryBinding
import uz.kozimjon.cleanarchitecture.databinding.AdapterNewBinding
import uz.kozimjon.cleanarchitecture.domain.model.NewsResponse
import uz.kozimjon.cleanarchitecture.presentation.model.Category

class NewAdapter(val listener: OnNewClickListener) : ListAdapter<NewsResponse.New, NewAdapter.NewViewHolder>(NewDiffUtil()) {

    interface OnNewClickListener {
        fun onNewClick(new: NewsResponse.New)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewViewHolder {
        val view = AdapterNewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewViewHolder, position: Int) {
        holder.onBind(position)
    }

    // ViewHolder
    inner class NewViewHolder(val binding: AdapterNewBinding): RecyclerView.ViewHolder(binding.root) {
        fun onBind(position: Int) {
            val item = getItem(position)

            Glide.with(itemView.context).load(item.image).centerCrop().placeholder(R.drawable.ic_launcher_background).into(binding.ivImage)
            binding.tvName.text = item.name ?: "No name"
            binding.tvDesc.text = item.description ?: "No desc"
            binding.tvPrice.text = "${item.net_price} $"

            itemView.setOnClickListener {
                listener.onNewClick(item)
            }
        }
    }

    // DiffUtil
    class NewDiffUtil : DiffUtil.ItemCallback<NewsResponse.New>() {
        override fun areItemsTheSame(oldItem: NewsResponse.New, newItem: NewsResponse.New): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: NewsResponse.New, newItem: NewsResponse.New): Boolean {
            return oldItem == newItem
        }
    }
}
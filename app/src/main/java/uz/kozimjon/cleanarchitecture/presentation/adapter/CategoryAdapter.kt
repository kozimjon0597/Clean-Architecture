package uz.kozimjon.cleanarchitecture.presentation.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import uz.kozimjon.cleanarchitecture.R
import uz.kozimjon.cleanarchitecture.databinding.AdapterCategoryBinding
import uz.kozimjon.cleanarchitecture.presentation.model.Category

class CategoryAdapter(val listener: OnCategoryClickListener) : ListAdapter<Category, CategoryAdapter.CategoryViewHolder>(CategoryDiffUtil()) {

    interface OnCategoryClickListener {
        fun onCategoryClick(category: Category)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = AdapterCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.onBind(position)
    }

    // ViewHolder
    inner class CategoryViewHolder(val binding: AdapterCategoryBinding): RecyclerView.ViewHolder(binding.root) {
        fun onBind(position: Int) {
            val item = getItem(position)

            binding.tvName.text = item.name ?: "No name"

            if (item.checked) {
                binding.rlItem.setBackgroundResource(R.drawable.background_checked_category)
                binding.tvName.setTextColor(Color.WHITE)
            } else {
                binding.rlItem.setBackgroundResource(R.drawable.background_category)
                binding.tvName.setTextColor(Color.parseColor("#6E6D6D"))
            }

            itemView.setOnClickListener {
                listener.onCategoryClick(item)
            }
        }
    }

    // DiffUtil
    class CategoryDiffUtil : DiffUtil.ItemCallback<Category>() {
        override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem == newItem
        }
    }
}
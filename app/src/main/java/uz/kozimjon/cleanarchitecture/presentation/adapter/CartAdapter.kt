package uz.kozimjon.cleanarchitecture.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import uz.kozimjon.cleanarchitecture.R
import uz.kozimjon.cleanarchitecture.databinding.AdapterCartBinding
import uz.kozimjon.cleanarchitecture.databinding.AdapterFavouriteBinding
import uz.kozimjon.cleanarchitecture.domain.model.NewsResponse
import uz.kozimjon.cleanarchitecture.presentation.model.Cart
import uz.kozimjon.cleanarchitecture.presentation.model.Favourite


class CartAdapter(private val listener: OnCartClickListener)
    : ListAdapter<Cart, CartAdapter.CartViewHolder>(CartDiffUtil()) {

    interface OnCartClickListener {
        fun onCartClick(cart: Cart)
        fun onCartDeleteClick(cart: Cart)
        fun onCartPlusClick(cart: Cart)
        fun onCartMinusClick(cart: Cart)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        return CartViewHolder(AdapterCartBinding
            .inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    // ViewHolder
    inner class CartViewHolder(val binding: AdapterCartBinding): RecyclerView.ViewHolder(binding.root) {
        fun onBind(item: Cart) {
            Glide.with(itemView.context).load(item.image).centerCrop().placeholder(R.drawable.ic_launcher_background).into(binding.ivImage)
            binding.tvName.text = item.name ?: "No name"
            binding.tvPrice.text = "${item.net_price} $"
            binding.tvValue.text = item.count.toString()

            itemView.setOnClickListener {
                listener.onCartClick(item)
            }

            binding.ivRemove.setOnClickListener {
                listener.onCartDeleteClick(item)
            }

            binding.tvMinus.setOnClickListener {
                listener.onCartMinusClick(item)
//                if (item.count!! > 1) {
//                    binding.tvValue.text = "${item.count - 1}"
//                }
            }

            binding.tvPlus.setOnClickListener {
                listener.onCartPlusClick(item)
//                binding.tvValue.text = "${item.count!! + 1}"
            }
        }
    }

    // DiffUtil
    class CartDiffUtil : DiffUtil.ItemCallback<Cart>() {
        override fun areItemsTheSame(oldItem: Cart, newItem: Cart): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Cart, newItem: Cart): Boolean {
            return oldItem == newItem
        }
    }
}
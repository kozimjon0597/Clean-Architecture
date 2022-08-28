package uz.kozimjon.cleanarchitecture.presentation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import uz.kozimjon.cleanarchitecture.databinding.FragmentCartBinding
import uz.kozimjon.cleanarchitecture.databinding.FragmentHomeBinding
import uz.kozimjon.cleanarchitecture.paper_db.Carts
import uz.kozimjon.cleanarchitecture.paper_db.Favourites
import uz.kozimjon.cleanarchitecture.presentation.adapter.CartAdapter
import uz.kozimjon.cleanarchitecture.presentation.model.Cart

class CartFragment : Fragment(), CartAdapter.OnCartClickListener {
    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!
    private lateinit var cartAdapter: CartAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (Carts.getCarts().isEmpty()) {
            binding.rlContent.visibility = View.GONE
            binding.llEmpty.visibility = View.VISIBLE
        } else {
            binding.llEmpty.visibility = View.GONE
            binding.rlContent.visibility = View.VISIBLE
        }

        cartAdapter = CartAdapter(this@CartFragment)
        binding.rvCarts.adapter = cartAdapter
        cartAdapter.submitList(Carts.getCarts())

        calculateTotalPrice()
    }

    override fun onCartClick(cart: Cart) {
        val action = CartFragmentDirections.actionNavigationCartToDetailFragment(
            cart.id!!,
            cart.name,
            cart.description,
            cart.image,
            cart.net_price!!
        )
        findNavController().navigate(action)
    }

    override fun onCartDeleteClick(cart: Cart) {
        Carts.removeCart(cart)
        val carts = Carts.getCarts()
        cartAdapter.submitList(carts)
        calculateTotalPrice()

        if (carts.isEmpty()) {
            binding.rlContent.visibility = View.GONE
            binding.llEmpty.visibility = View.VISIBLE
        }
    }

    override fun onCartPlusClick(cart: Cart) {
        val updatedCount = cart.count!! + 1
        val updatedCart = Cart(cart.id, cart.name, cart.description, cart.image, cart.net_price, updatedCount)
        Carts.updateCart(updatedCart)
        cartAdapter.submitList(Carts.getCarts())
        calculateTotalPrice()
    }

    override fun onCartMinusClick(cart: Cart) {
        if (cart.count!! > 1) {
            val updatedCount = cart.count - 1
            val updatedCart = Cart(cart.id, cart.name, cart.description, cart.image, cart.net_price, updatedCount)
            Carts.updateCart(updatedCart)
            cartAdapter.submitList(Carts.getCarts())
            calculateTotalPrice()
        }
    }

    private fun calculateTotalPrice() {

        var totalPrice = 0f
        for (element in Carts.getCarts()) {
            totalPrice += element.count!! * element.net_price!!
        }

        binding.tvTotalValue.text = "$totalPrice $"
    }
}
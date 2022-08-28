package uz.kozimjon.cleanarchitecture.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import uz.kozimjon.cleanarchitecture.R
import uz.kozimjon.cleanarchitecture.databinding.FragmentDetailBinding
import uz.kozimjon.cleanarchitecture.paper_db.Carts
import uz.kozimjon.cleanarchitecture.paper_db.Favourites
import uz.kozimjon.cleanarchitecture.presentation.model.Cart
import uz.kozimjon.cleanarchitecture.presentation.model.Favourite

class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id: Int? = arguments?.getInt("id")
        val name: String? = arguments?.getString("name")
        val image: String? = arguments?.getString("image")
        val desc: String? = arguments?.getString("desc")
        val netPrice: Float? = arguments?.getFloat("net_price")

        val favourite = Favourite(id, name, desc, image, netPrice)

        Glide.with(requireContext()).load(image).centerCrop().placeholder(R.drawable.ic_launcher_background).into(binding.ivImage)
        binding.tvPrice.text = netPrice.toString() + " $"
        binding.tvName.text = name
        binding.tvDesc.text = desc

        if (Favourites.existFavourite(favourite)) {
            binding.ivBookmark.setImageResource(R.drawable.ic_baseline_favorite_24_white)
        } else {
            binding.ivBookmark.setImageResource(R.drawable.ic_baseline_favorite_border_24_white)
        }

        binding.ivBookmark.setOnClickListener {
            if (Favourites.existFavourite(favourite)) {
                binding.ivBookmark.setImageResource(R.drawable.ic_baseline_favorite_border_24_white)
                Favourites.removeFavourite(favourite)
                Toast.makeText(requireContext(), "Saralanganlardan olindi", Toast.LENGTH_SHORT).show()

            } else {
                binding.ivBookmark.setImageResource(R.drawable.ic_baseline_favorite_24_white)
                Favourites.addFavourite(favourite)
                Toast.makeText(requireContext(), "Saralanganlarga qo'shildi", Toast.LENGTH_SHORT).show()
            }
        }

        binding.tvAddToCart.setOnClickListener {
            val cart = Cart(id, name, desc, image, netPrice, 1)

            if (Carts.existCart(cart)) {
                Toast.makeText(requireContext(), "Ushbu mahsulot Savatda mavjud", Toast.LENGTH_SHORT).show()
            } else {
                Carts.addCart(cart)
                Toast.makeText(requireContext(), "Mahsulot savatga qo'shildi", Toast.LENGTH_SHORT).show()
            }
        }

        binding.ivBack.setOnClickListener {
            findNavController().navigateUp()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
package uz.kozimjon.cleanarchitecture.presentation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import uz.kozimjon.cleanarchitecture.databinding.FragmentFavouriteBinding
import uz.kozimjon.cleanarchitecture.databinding.FragmentHomeBinding
import uz.kozimjon.cleanarchitecture.paper_db.Favourites
import uz.kozimjon.cleanarchitecture.presentation.adapter.FavouriteAdapter
import uz.kozimjon.cleanarchitecture.presentation.model.Favourite

class FavouriteFragment : Fragment(), FavouriteAdapter.OnFavouriteClickListener {
    private var _binding: FragmentFavouriteBinding? = null
    private val binding get() = _binding!!
    private lateinit var favouriteAdapter: FavouriteAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentFavouriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (Favourites.getFavourites().isEmpty()) {
            binding.llEmpty.visibility = View.VISIBLE
        } else {
            binding.llEmpty.visibility = View.GONE
        }

        favouriteAdapter = FavouriteAdapter(this@FavouriteFragment)
        binding.rvFavourites.adapter = favouriteAdapter
        favouriteAdapter.submitList(Favourites.getFavourites())
    }

    override fun onFavouriteClick(favourite: Favourite) {
        val action = FavouriteFragmentDirections.actionNavigationFavouriteToDetailFragment(
            favourite.id!!,
            favourite.name,
            favourite.description,
            favourite.image,
            favourite.net_price!!
        )
        findNavController().navigate(action)
    }

    override fun onFavouriteDeleteClick(favourite: Favourite) {
        Favourites.removeFavourite(favourite)
        val favourites = Favourites.getFavourites()
        favouriteAdapter.submitList(favourites)

        if (favourites.isEmpty()) {
            binding.llEmpty.visibility = View.VISIBLE
        }
    }
}
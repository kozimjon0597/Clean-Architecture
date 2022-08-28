package uz.kozimjon.cleanarchitecture.presentation.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.zhpan.indicator.enums.IndicatorSlideMode
import com.zhpan.indicator.enums.IndicatorStyle
import kotlinx.coroutines.launch
import uz.kozimjon.cleanarchitecture.R
import uz.kozimjon.cleanarchitecture.databinding.FragmentHomeBinding
import uz.kozimjon.cleanarchitecture.domain.model.NewsResponse
import uz.kozimjon.cleanarchitecture.presentation.adapter.CategoryAdapter
import uz.kozimjon.cleanarchitecture.presentation.adapter.NewAdapter
import uz.kozimjon.cleanarchitecture.presentation.adapter.SliderAdapter
import uz.kozimjon.cleanarchitecture.presentation.model.Category
import uz.kozimjon.cleanarchitecture.utils.App
import uz.kozimjon.cleanarchitecture.utils.NewResource
import uz.kozimjon.cleanarchitecture.presentation.viewmodels.NewsViewModel
import javax.inject.Inject


class HomeFragment : Fragment(), CategoryAdapter.OnCategoryClickListener,
    NewAdapter.OnNewClickListener {
    @Inject
    lateinit var newsViewModel: NewsViewModel
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var newAdapter: NewAdapter
    private lateinit var categoryAdapter: CategoryAdapter
    private val categories = ArrayList<Category>()

    private lateinit var handler: Handler
    private lateinit var runnable: Runnable
    private var list = ArrayList<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadData()
        setViewPager()

        binding.llError.setOnClickListener {
            loadData()
        }
    }

    // Functions

    private fun loadData() {
        lifecycleScope.launch {
            newsViewModel.getNews()
                .collect {
                    when (it) {
                        is NewResource.Loading -> {
                            loadingVisible()
                        }
                        is NewResource.Success -> {
                            for (element in it.newsResponse?.data!!) {
                                categories.add(Category(element.name))
                            }

                            categoryAdapter = CategoryAdapter(this@HomeFragment)
                            binding.rvCategories.adapter = categoryAdapter
                            categoryAdapter.submitList(categories)

                            newAdapter = NewAdapter(this@HomeFragment)
                            binding.rvNews.adapter = newAdapter
                            newAdapter.submitList(it.newsResponse.data)

                            screenVisible()
                        }
                        is NewResource.Error -> {
                            Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_SHORT).show()
                            errorVisible()
                        }
                    }
                }
        }

    }

    private fun screenVisible() {
        binding.nsv.visibility = View.VISIBLE
        binding.progressBar.visibility = View.GONE
        binding.llError.visibility = View.GONE
    }

    private fun loadingVisible() {
        binding.progressBar.visibility = View.VISIBLE
        binding.llError.visibility = View.GONE
        binding.nsv.visibility = View.GONE
    }

    private fun errorVisible() {
        binding.llError.visibility = View.VISIBLE
        binding.progressBar.visibility = View.GONE
        binding.nsv.visibility = View.GONE
    }


    override fun onCategoryClick(category: Category) {
        categories.forEach {
            it.checked = false
        }

        category.checked = true
        categoryAdapter.submitList(categories)
        categoryAdapter.notifyDataSetChanged()
    }

    override fun onNewClick(new: NewsResponse.New) {
        val action = HomeFragmentDirections.actionNavigationHomeToDetailFragment(
            new.id!!,
            new.name,
            new.description,
            new.image,
            new.net_price!!)
        findNavController().navigate(action)
    }

    private fun setViewPager() {
        list = ArrayList()
        list.add(R.drawable.image_1)
        list.add(R.drawable.image_2)
        list.add(R.drawable.image_4)

        val sliderAdapter = SliderAdapter(list)
        binding.viewpager.adapter = sliderAdapter
        binding.viewpager.isUserInputEnabled = false

        setIndicator()
        setAutoSlider()
    }

    private fun setAutoSlider() {
        handler = Handler(Looper.myLooper()!!)
        runnable = Runnable {
            binding.viewpager.currentItem = binding.viewpager.currentItem + 1
        }

        binding.viewpager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                handler.removeCallbacks(runnable)
                handler.postDelayed(runnable, 3000)

                if (position == list.size - 1) {
                    Handler(Looper.getMainLooper()).postDelayed({
                        binding.viewpager.currentItem = 0
                    }, 3000)
                }
            }
        })
    }

    private fun setIndicator() {
        binding.indicatorView.apply {
            setSliderWidth(resources.getDimension(R.dimen.dp_10))
            setSliderHeight(resources.getDimension(R.dimen.dp_10))
            setSlideMode(IndicatorSlideMode.WORM)
            setIndicatorStyle(IndicatorStyle.CIRCLE)
            setupWithViewPager(binding.viewpager)
        }
    }

    override fun onDetach() {
        super.onDetach()
        handler.removeCallbacks(runnable)
    }

    override fun onStop() {
        super.onStop()
        handler.removeCallbacks(runnable)
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(runnable)
    }

    override fun onResume() {
        super.onResume()
        handler.postDelayed(runnable, 3000)

    }
}
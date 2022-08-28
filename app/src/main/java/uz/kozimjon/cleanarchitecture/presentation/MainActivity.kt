package uz.kozimjon.cleanarchitecture.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import uz.kozimjon.cleanarchitecture.R
import uz.kozimjon.cleanarchitecture.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
//    @Inject
//    lateinit var factory: ViewModelProvider.Factory
//    private val viewModel : NewsViewModel by viewModels {factory}

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        binding.navView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.navigation_home -> showBottomNav()
                R.id.navigation_cart -> showBottomNav()
                R.id.navigation_favourite -> showBottomNav()
                R.id.navigation_user -> showBottomNav()
                else -> hideBottomNav()
            }
        }
    }

    private fun showBottomNav() {
        binding.navView.visibility = View.VISIBLE

    }

    private fun hideBottomNav() {
        binding.navView.visibility = View.GONE
    }
}
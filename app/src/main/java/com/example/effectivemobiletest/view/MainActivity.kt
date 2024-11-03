package com.example.effectivemobiletest.view

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.effectivemobiletest.R
import com.example.effectivemobiletest.databinding.ActivityMainBinding
import com.example.effectivemobiletest.viewModel.VacanciesViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.sql.Time

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: VacanciesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initState()
    }

    private fun initState() {
        viewModel.fetchData()
        setupNavigation()
        setObservers()
    }

    private fun setupNavigation() {

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.main_fragment_container) as NavHostFragment
        val navController = navHostFragment.navController

        binding.bottomNavigation.setupWithNavController(navController)

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_search -> navController.navigate(R.id.searchFragment)
                R.id.nav_favorites -> navController.navigate(R.id.favoritesFragment)
                else -> navController.navigate(R.id.plugFragment)
            }
            true
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.searchFragment -> binding.bottomNavigation.menu.findItem(R.id.nav_search).isChecked = true
                R.id.favoritesFragment -> binding.bottomNavigation.menu.findItem(R.id.nav_favorites).isChecked = true
                R.id.plugFragment -> {
                    binding.bottomNavigation.menu.findItem(R.id.nav_responses).isChecked = true
                }
            }
        }
    }

    private fun setObservers() {

        viewModel.favoriteVacanciesCount.observe(this) {
            val bottomNavigationView = binding.bottomNavigation
            val badge = bottomNavigationView.getOrCreateBadge(R.id.nav_favorites)

            if (it > 0) {
                badge.isVisible = true
                badge.number = it
                badge.backgroundColor = ContextCompat.getColor(this, R.color.red)
                badge.badgeTextColor = ContextCompat.getColor(this, R.color.white) // Цвет текста бейджа
            } else {
                badge.isVisible = false
            }
        }
    }

}
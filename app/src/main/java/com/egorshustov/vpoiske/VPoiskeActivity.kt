package com.egorshustov.vpoiske

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.egorshustov.vpoiske.userlist.UserListViewModel
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_v_poiske.*
import javax.inject.Inject

class VPoiskeActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<UserListViewModel> { viewModelFactory }

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(viewModel.currentTheme.themeId)
        setContentView(R.layout.activity_v_poiske)
        setupNavigation()
        setChangeThemeListener()
        observeSearchState()
    }

    private fun setupNavigation() {
        setSupportActionBar(findViewById(R.id.toolbar))
        val navController = findNavController(R.id.nav_host_fragment)
        appBarConfiguration = AppBarConfiguration(navController.graph, drawer_layout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        nav_view.setupWithNavController(navController)
    }

    private fun setChangeThemeListener() {
        val navChangeTheme = nav_view.menu.findItem(R.id.nav_change_theme)
        navChangeTheme.setOnMenuItemClickListener {
            viewModel.onNavChangeThemeClicked()
            recreate()
            true
        }
    }

    private fun observeSearchState() {
        viewModel.searchState.observe(this, Observer {
            nav_view.menu.findItem(R.id.newSearchFragment).isVisible =
                it == UserListViewModel.SearchState.INACTIVE
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.nav_host_fragment).navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}
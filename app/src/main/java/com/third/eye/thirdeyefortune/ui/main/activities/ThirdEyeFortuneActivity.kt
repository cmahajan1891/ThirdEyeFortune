package com.third.eye.thirdeyefortune.ui.main.activities

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.third.eye.thirdeyefortune.R
import com.third.eye.thirdeyefortune.databinding.ThirdEyeFortuneActivityBinding
import com.third.eye.thirdeyefortune.di.components.ActivityComponent
import com.third.eye.thirdeyefortune.ui.base.BaseActivity
import com.third.eye.thirdeyefortune.ui.main.viewModels.ThirdEyeFortuneActivityViewModel
import com.third.eye.thirdeyefortune.utils.common.toggleVisibility

class ThirdEyeFortuneActivity :
    BaseActivity<ThirdEyeFortuneActivityViewModel, ThirdEyeFortuneActivityBinding>() {

    private lateinit var bottomNavigationView: BottomNavigationView

    override fun provideLayoutId(): Int = R.layout.third_eye_fortune_activity

    override fun injectDependencies(activityComponent: ActivityComponent) {
        activityComponent.inject(this)
    }

    override fun setupView(savedInstanceState: Bundle?) {
        bottomNavigationView = binding.bottomNav
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment?
        NavigationUI.setupWithNavController(bottomNavigationView, navHostFragment!!.navController)
    }

    fun showBottomNavigation() {
        if (this::bottomNavigationView.isInitialized) {
            bottomNavigationView.toggleVisibility(View.VISIBLE)
        }
    }

    fun hideBottomNavigation() {
        if (this::bottomNavigationView.isInitialized) {
            bottomNavigationView.toggleVisibility(View.GONE)
        }
    }

    override fun onSupportNavigateUp() = findNavController(R.id.nav_host_fragment).navigateUp()

}

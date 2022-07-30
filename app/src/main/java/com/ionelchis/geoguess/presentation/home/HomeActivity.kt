package com.ionelchis.geoguess.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.ionelchis.geoguess.base.BaseActivity
import com.ionelchis.geoguess.databinding.ActivityHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity :
    BaseActivity<HomeIntent, HomeState, HomeEffect, HomeViewModel, ActivityHomeBinding>() {

    override val viewModel: HomeViewModel by viewModels()

    override fun getViewBinding(inflater: LayoutInflater): ActivityHomeBinding =
        ActivityHomeBinding.inflate(inflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
    }
}
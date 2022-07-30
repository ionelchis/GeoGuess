package com.ionelchis.geoguess.presentation.home

import com.ionelchis.geoguess.base.BaseViewModel

class HomeViewModel : BaseViewModel<HomeIntent, HomeState, HomeEffect>() {
    override fun createInitialState(): HomeState = HomeState()

    override fun handleIntent(intent: HomeIntent) {
        TODO("Not yet implemented")
    }
}
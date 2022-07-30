package com.ionelchis.geoguess.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.ionelchis.geoguess.ext.collectLifecycleFlow

abstract class BaseActivity<
        INTENT : Intent,
        STATE : State,
        EFFECT : Effect,
        VM : BaseViewModel<INTENT, STATE, EFFECT>,
        VB : ViewBinding>
    : AppCompatActivity(),
    StateRenderer<STATE>, EffectRenderer<EFFECT>,
    ViewInitializer {

    protected abstract val viewModel: VM
    protected lateinit var binding: VB
    protected lateinit var currentState: STATE

    protected abstract fun getViewBinding(inflater: LayoutInflater): VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = getViewBinding(layoutInflater)
        setContentView(binding.root)

        initUI()
        observeData()
        initData()
        initListeners()
    }

    private fun observeData() {
        collectLifecycleFlow(viewModel.state) {
            currentState = it
            renderState(it)
        }

        collectLifecycleFlow(viewModel.effect, this::renderEffect)
    }

    protected fun dispatchIntent(intent: INTENT) {
        viewModel.dispatchIntent(intent)
    }
}
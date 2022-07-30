package com.ionelchis.geoguess.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.ionelchis.geoguess.ext.collectLifecycleFlow

abstract class BaseDialogFragment<
        INTENT : Intent,
        STATE : State,
        EFFECT : Effect,
        VM : BaseViewModel<INTENT, STATE, EFFECT>,
        VB : ViewBinding>
    : DialogFragment(),
    StateRenderer<STATE>, EffectRenderer<EFFECT>,
    ViewInitializer {

    protected abstract val viewModel: VM
    protected lateinit var binding: VB
    protected lateinit var currentState: STATE

    protected abstract fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): VB

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = getViewBinding(inflater, container)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
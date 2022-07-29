package com.ionelchis.geoguess.base

interface StateRenderer<STATE : State> {
    fun renderState(state: STATE) {}
}

interface EffectRenderer<EFFECT : Effect> {
    fun renderEffect(effect: EFFECT) {}
}
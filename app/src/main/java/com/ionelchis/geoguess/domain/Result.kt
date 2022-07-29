package com.ionelchis.geoguess.domain

sealed class Result<T>(
    open val data: T? = null,
    open val exception: Exception? = null
) {
    data class Success<T>(override val data: T) : Result<T>(data)

    data class Error<T>(override val exception: Exception) : Result<T>(null, exception)
}
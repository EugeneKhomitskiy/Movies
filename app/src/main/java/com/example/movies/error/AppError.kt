package com.example.movies.error

import java.io.IOException

sealed class AppError(val code: String) : RuntimeException() {
    companion object {
        fun from(e: Throwable): AppError = when (e) {
            is AppError -> e
            is IOException -> NetworkError
            else -> UnknownError
        }
    }
}

class ApiError(val status: Int, code: String) : AppError(code)
object NetworkError : AppError("error_network")
object UnknownError : AppError("error_unknown")
package com.example.prayertimes.domain.usecase

import retrofit2.Response
abstract class BaseRepository {

    protected suspend fun <T> wrapApiCall(call: suspend () -> Response<T>): T {
        return try {
            call().takeIf { it.isSuccessful }?.body() ?: throw Throwable()
        }catch (e: Exception) {
            throw Throwable(e.message)
        }
    }


}
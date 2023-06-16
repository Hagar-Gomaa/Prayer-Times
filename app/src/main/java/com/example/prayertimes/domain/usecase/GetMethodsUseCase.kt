package com.example.prayertimes.domain.usecase

import com.example.prayertimes.domain.Repository
import com.example.prayertimes.domain.entities.MethodsEntity
import javax.inject.Inject

class GetMethodsUseCase @Inject constructor(private val repository: Repository) {

    suspend operator fun invoke ():MethodsEntity {
        return repository.getMethods()
    }
}
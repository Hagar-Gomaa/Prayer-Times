package com.example.prayertimes.domain

import com.example.prayertimes.domain.entities.MethodsEntity

interface Repository {
suspend fun getMethods(): MethodsEntity
}
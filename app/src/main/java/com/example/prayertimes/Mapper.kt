package com.example.prayertimes

interface Mapper<I,O> {
    fun map ( input:I):O
}
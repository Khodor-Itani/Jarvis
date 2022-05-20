package com.kdz.jarvis.network.models.containers

data class BaseDataContainer<T>(
    val count: String,
    val limit: String,
    val offset: String,
    val results: List<T>,
    val total: String
)
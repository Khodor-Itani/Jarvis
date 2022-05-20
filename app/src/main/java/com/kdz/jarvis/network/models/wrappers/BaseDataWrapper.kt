package com.kdz.jarvis.network.models.wrappers

import com.kdz.jarvis.network.models.containers.BaseDataContainer

data class BaseDataWrapper<T>(
    val attributionHTML: String,
    val attributionText: String,
    val code: String,
    val copyright: String,
    val `data`: BaseDataContainer<T>,
    val etag: String,
    val status: String
)
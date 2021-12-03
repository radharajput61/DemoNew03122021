package com.app.demo.model

data class APIResponse(
    val `data`: List<Data>,
    val responseCode: ResponseCode
)
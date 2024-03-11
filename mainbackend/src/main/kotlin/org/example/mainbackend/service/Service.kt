package org.example.mainbackend.service

import org.example.mainbackend.client.RecommendationsClient
import org.example.mainbackend.dto.ProductsHistory
import org.springframework.stereotype.Service

@Service
class Service(
    private val recommendationsClient: RecommendationsClient,
) {
    fun doRecommendation() {
        recommendationsClient.createTask(products = ProductsHistory(listOf()))
    }
}

package org.example.mainbackend.client

import org.example.mainbackend.dto.ProductsHistory
import org.example.mainbackend.dto.Recommendation
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping

@Component
@FeignClient(value = "python", url = "https://localhost:8081/")
interface RecommendationsClient {
    @PostMapping("/recommendations")
    fun createTask(products: ProductsHistory): Long

    @GetMapping("/result/{taskId}")
    fun getResult(
        @PathVariable("taskId") taskId: Long,
    ): Recommendation
}

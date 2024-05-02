package org.example.mainbackend.controller

import org.example.mainbackend.dto.ProductDto
import org.example.mainbackend.dto.ProductsDto
import org.example.mainbackend.model.User
import org.example.mainbackend.service.ProductsService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/products")
class ProductsController(
    private val productsService: ProductsService,
) {
    @GetMapping("/history")
    fun getProductHistory(
        @AuthenticationPrincipal user: User,
    ): ProductsDto {
        return ProductsDto(productsService.findByUser(user).map { ProductDto(it) })
    }

    @GetMapping("/active")
    fun getActiveProducts(
        @AuthenticationPrincipal user: User,
    ): ProductsDto {
        return ProductsDto(productsService.findActiveByUser(user).map { ProductDto(it) })
    }

    // @PostMapping
    // fun addProduct(
    //     @AuthenticationPrincipal user: User,
    //     @RequestBody product: ProductDto,
    // ): ProductDto {
    //     return ProductDto(productsService.addProductToUser(Product(product), user))
    // }
    //
    // @GetMapping("/delete/{id}")
    // fun deleteProduct(
    //     @PathVariable id: Long,
    //     @AuthenticationPrincipal user: User,
    // ): ProductDto {
    //     return ProductDto(productsService.deleteProductByIdAndUser(id, user))
    // }
    //
    // @PostMapping("/edit")
    // fun editProduct(
    //     @AuthenticationPrincipal user: User,
    //     @RequestBody product: ProductDto,
    // ): ProductDto {
    //     return ProductDto(productsService.editProduct(Product(product), user))
    // }
}

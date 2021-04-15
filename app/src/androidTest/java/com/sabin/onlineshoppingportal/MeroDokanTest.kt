package com.sabin.onlineshoppingportal

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.filters.LargeTest
import com.sabin.onlineshoppingportal.api.ServiceBuilder
import com.sabin.onlineshoppingportal.entity.Cart
import com.sabin.onlineshoppingportal.entity.Order
import com.sabin.onlineshoppingportal.entity.Product
import com.sabin.onlineshoppingportal.repository.CartRepository
import com.sabin.onlineshoppingportal.repository.OrderRepository
import com.sabin.onlineshoppingportal.repository.ProductRepository
import com.sabin.onlineshoppingportal.repository.UserRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

class MeroDokanTest {
    private lateinit var userRepository: UserRepository
    private lateinit var productRepository: ProductRepository
    private lateinit var cartRepository: CartRepository
    private lateinit var orderRepository: OrderRepository

    @Test

    // Login Test
    fun checkLogin() = runBlocking{
        userRepository = UserRepository()
        val response = userRepository.checkUser("sabin","sabin")
        val expectedResult = true
        val actualResult = response.success
        Assert.assertEquals(expectedResult,actualResult)
    }
    // Get all products
    @Test
    fun checkGetAllProducts() = runBlocking {
        userRepository = UserRepository()
        ServiceBuilder.token = "Bearer " + userRepository.checkUser("sabin","sabin").token
        productRepository = ProductRepository()
        val response = productRepository.getAllProducts()
        val expectedResult = true
        val actualResult = response.success
        Assert.assertEquals(expectedResult,actualResult)
    }
    // Add to Cart
    @Test
    fun checkAddtoCart() = runBlocking {
        userRepository = UserRepository()
        ServiceBuilder.token = "Bearer " + userRepository.checkUser("ramesh","ramesh").token
        cartRepository = CartRepository()
        val cart = Cart(quantity = "5",)
        val response = cartRepository.addtoCart("6076cac1758f0515ecb02075", cart)
        val expectedResult = true
        val actualResult = response.success
        Assert.assertEquals(expectedResult,actualResult)
    }
    // Add Product
    @Test
    fun AddProduct() = runBlocking {
        userRepository = UserRepository()
        ServiceBuilder.token = "Bearer " + userRepository.checkUser("sabin","sabin").token
        productRepository = ProductRepository()
        val product = Product(name = "Charger", dec = "Good Quality Charger", price = "500", category = "Electronics", image = "noimage.jpg")
        val response = productRepository.addProduct(product)
        val expectedResult = true
        val actualResult = response.success
        Assert.assertEquals(expectedResult,actualResult)
    }
    // User Profile
    @Test
    fun ViewUser() = runBlocking {
        userRepository = UserRepository()
        ServiceBuilder.token = "Bearer " + userRepository.checkUser("sabin","sabin").token
        val response = userRepository.getSingleUser()
        val expectedResult = true
        val actualResult = response.success
        Assert.assertEquals(expectedResult,actualResult)
    }
    // Get Orders
    @Test
    fun GetOrders() = runBlocking {
        userRepository = UserRepository()
        ServiceBuilder.token = "Bearer " + userRepository.checkUser("sabin","sabin").token
        orderRepository = OrderRepository()
        val response = orderRepository.getOrder()
        val expectedResult = true
        val actualResult = response.success
        Assert.assertEquals(expectedResult,actualResult)
    }
}
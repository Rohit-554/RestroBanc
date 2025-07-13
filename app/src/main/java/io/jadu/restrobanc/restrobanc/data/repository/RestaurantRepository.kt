package io.jadu.restrobanc.restrobanc.data.repository

import io.jadu.restrobanc.restrobanc.data.api.RestaurantApiService
import io.jadu.restrobanc.restrobanc.data.model.GetItemByFilterRequest
import io.jadu.restrobanc.restrobanc.data.model.GetItemByFilterResponse
import io.jadu.restrobanc.restrobanc.data.model.GetItemByIdResponse
import io.jadu.restrobanc.restrobanc.data.model.GetItemListResponse
import io.jadu.restrobanc.restrobanc.data.model.MakePaymentRequest
import io.jadu.restrobanc.restrobanc.data.model.MakePaymentResponse

class RestaurantRepository {
    private val apiService = RestaurantApiService()
    
    suspend fun getItemList(page: Int = 1, count: Int = 10): Result<GetItemListResponse> {
        return apiService.getItemList(page, count)
    }
    
    suspend fun getItemById(itemId: Long): Result<GetItemByIdResponse> {
        return apiService.getItemById(itemId)
    }
    
    suspend fun getItemByFilter(filterRequest: GetItemByFilterRequest): Result<GetItemByFilterResponse> {
        return apiService.getItemByFilter(filterRequest)
    }
    
    suspend fun makePayment(paymentRequest: MakePaymentRequest): Result<MakePaymentResponse> {
        return apiService.makePayment(paymentRequest)
    }
}

package io.jadu.restrobanc.restrobanc.data.api


import io.jadu.restrobanc.restrobanc.data.model.GetItemByFilterRequest
import io.jadu.restrobanc.restrobanc.data.model.GetItemByFilterResponse
import io.jadu.restrobanc.restrobanc.data.model.GetItemByIdResponse
import io.jadu.restrobanc.restrobanc.data.model.GetItemListResponse
import io.jadu.restrobanc.restrobanc.data.model.MakePaymentRequest
import io.jadu.restrobanc.restrobanc.data.model.MakePaymentResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody

class RestaurantApiService {
    private val client = OkHttpClient()
    private val json = Json { ignoreUnknownKeys = true }
    
    companion object {
        private const val BASE_URL = "https://uat.onebanc.ai"
        private const val API_KEY = "uonebancservceemultrS3cg8RaL30"
        private const val CONTENT_TYPE = "application/json"
    }
    
    suspend fun getItemList(page: Int = 1, count: Int = 10): Result<GetItemListResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val requestBody = json.encodeToString(mapOf("page" to page, "count" to count))
                
                val request = Request.Builder()
                    .url("$BASE_URL/emulator/interview/get_item_list")
                    .addHeader("X-Partner-API-Key", API_KEY)
                    .addHeader("X-Forward-Proxy-Action", "get_item_list")
                    .addHeader("Content-Type", CONTENT_TYPE)
                    .post(requestBody.toRequestBody(CONTENT_TYPE.toMediaType()))
                    .build()
                
                val response = client.newCall(request).execute()
                if (response.isSuccessful) {
                    val responseBody = response.body?.string()
                    if (responseBody != null) {
                        val itemListResponse = json.decodeFromString<GetItemListResponse>(responseBody)
                        Result.success(itemListResponse)
                    } else {
                        Result.failure(Exception("Empty response body"))
                    }
                } else {
                    Result.failure(Exception("HTTP ${response.code}: ${response.message}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
    
    suspend fun getItemById(itemId: Long): Result<GetItemByIdResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val requestBody = json.encodeToString(mapOf("item_id" to itemId))
                
                val request = Request.Builder()
                    .url("$BASE_URL/emulator/interview/get_item_by_id")
                    .addHeader("X-Partner-API-Key", API_KEY)
                    .addHeader("X-Forward-Proxy-Action", "get_item_by_id")
                    .addHeader("Content-Type", CONTENT_TYPE)
                    .post(requestBody.toRequestBody(CONTENT_TYPE.toMediaType()))
                    .build()
                
                val response = client.newCall(request).execute()
                if (response.isSuccessful) {
                    val responseBody = response.body?.string()
                    if (responseBody != null) {
                        val itemResponse = json.decodeFromString<GetItemByIdResponse>(responseBody)
                        Result.success(itemResponse)
                    } else {
                        Result.failure(Exception("Empty response body"))
                    }
                } else {
                    Result.failure(Exception("HTTP ${response.code}: ${response.message}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
    
    suspend fun getItemByFilter(filterRequest: GetItemByFilterRequest): Result<GetItemByFilterResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val requestBody = json.encodeToString(filterRequest)
                
                val request = Request.Builder()
                    .url("$BASE_URL/emulator/interview/get_item_by_filter")
                    .addHeader("X-Partner-API-Key", API_KEY)
                    .addHeader("X-Forward-Proxy-Action", "get_item_by_filter")
                    .addHeader("Content-Type", CONTENT_TYPE)
                    .post(requestBody.toRequestBody(CONTENT_TYPE.toMediaType()))
                    .build()
                
                val response = client.newCall(request).execute()
                if (response.isSuccessful) {
                    val responseBody = response.body?.string()
                    if (responseBody != null) {
                        val filterResponse = json.decodeFromString<GetItemByFilterResponse>(responseBody)
                        Result.success(filterResponse)
                    } else {
                        Result.failure(Exception("Empty response body"))
                    }
                } else {
                    Result.failure(Exception("HTTP ${response.code}: ${response.message}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
    
    suspend fun makePayment(paymentRequest: MakePaymentRequest): Result<MakePaymentResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val requestBody = json.encodeToString(paymentRequest)
                
                val request = Request.Builder()
                    .url("$BASE_URL/emulator/interview/make_payment")
                    .addHeader("X-Partner-API-Key", API_KEY)
                    .addHeader("X-Forward-Proxy-Action", "make_payment")
                    .addHeader("Content-Type", CONTENT_TYPE)
                    .post(requestBody.toRequestBody(CONTENT_TYPE.toMediaType()))
                    .build()
                
                val response = client.newCall(request).execute()
                if (response.isSuccessful) {
                    val responseBody = response.body?.string()
                    if (responseBody != null) {
                        val paymentResponse = json.decodeFromString<MakePaymentResponse>(responseBody)
                        Result.success(paymentResponse)
                    } else {
                        Result.failure(Exception("Empty response body"))
                    }
                } else {
                    Result.failure(Exception("HTTP ${response.code}: ${response.message}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
}

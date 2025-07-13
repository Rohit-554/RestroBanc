package io.jadu.restrobanc.restrobanc.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetItemListResponse(
    @SerialName("response_code") val responseCode: Int,
    @SerialName("outcome_code") val outcomeCode: Int,
    @SerialName("response_message") val responseMessage: String,
    val page: Int,
    val count: Int,
    @SerialName("total_pages") val totalPages: Int,
    @SerialName("total_items") val totalItems: Int,
    val cuisines: List<Cuisine>
)

@Serializable
data class Cuisine(
    @SerialName("cuisine_id") val cuisineId: String,
    @SerialName("cuisine_name") val cuisineName: String,
    @SerialName("cuisine_image_url") val cuisineImageUrl: String,
    val items: List<Item>
)

@Serializable
data class Item(
    val id: String,
    val name: String,
    @SerialName("image_url") val imageUrl: String,
    val price: String,
    val rating: String
)

@Serializable
data class GetItemByIdResponse(
    @SerialName("response_code") val responseCode: Int,
    @SerialName("outcome_code") val outcomeCode: Int,
    @SerialName("response_message") val responseMessage: String,
    @SerialName("cuisine_id") val cuisineId: String,
    @SerialName("cuisine_name") val cuisineName: String,
    @SerialName("cuisine_image_url") val cuisineImageUrl: String,
    @SerialName("item_id") val itemId: Long,
    @SerialName("item_name") val itemName: String,
    @SerialName("item_price") val itemPrice: Int,
    @SerialName("item_rating") val itemRating: Double,
    @SerialName("item_image_url") val itemImageUrl: String
)

@Serializable
data class GetItemByFilterRequest(
    @SerialName("cuisine_type") val cuisineType: List<String>? = null,
    @SerialName("price_range") val priceRange: PriceRange? = null,
    @SerialName("min_rating") val minRating: Int? = null
)

@Serializable
data class PriceRange(
    @SerialName("min_amount") val minAmount: Int,
    @SerialName("max_amount") val maxAmount: Int
)

@Serializable
data class GetItemByFilterResponse(
    @SerialName("response_code") val responseCode: Int,
    @SerialName("outcome_code") val outcomeCode: Int,
    @SerialName("response_message") val responseMessage: String,
    val cuisines: List<Cuisine>
)

@Serializable
data class MakePaymentRequest(
    @SerialName("total_amount") val totalAmount: String,
    @SerialName("total_items") val totalItems: Int,
    val data: List<PaymentItem>
)

@Serializable
data class PaymentItem(
    @SerialName("cuisine_id") val cuisineId: Int,
    @SerialName("item_id") val itemId: Int,
    @SerialName("item_price") val itemPrice: Int,
    @SerialName("item_quantity") val itemQuantity: Int
)

@Serializable
data class MakePaymentResponse(
    @SerialName("response_code") val responseCode: Int,
    @SerialName("outcome_code") val outcomeCode: Int,
    @SerialName("response_message") val responseMessage: String,
    @SerialName("txn_ref_no") val txnRefNo: String
)

// UI Models
data class CartItem(
    val cuisineId: String,
    val itemId: String,
    val name: String,
    val price: Int,
    val imageUrl: String,
    var quantity: Int = 0
)

data class CartSummary(
    val items: List<CartItem>,
    val subtotal: Double,
    val cgst: Double,
    val sgst: Double,
    val total: Double
)

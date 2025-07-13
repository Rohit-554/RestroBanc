package io.jadu.restrobanc.restrobanc.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import io.jadu.restrobanc.restrobanc.data.model.CartItem
import io.jadu.restrobanc.restrobanc.data.model.CartSummary
import io.jadu.restrobanc.restrobanc.data.model.Item
import io.jadu.restrobanc.restrobanc.ui.components.CompatFilledIconButton
import io.jadu.restrobanc.restrobanc.ui.components.DishCard
import io.jadu.restrobanc.restrobanc.ui.components.LoadingScreen
import io.jadu.restrobanc.restrobanc.ui.viewmodel.RestaurantViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(
    viewModel: RestaurantViewModel,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState = viewModel.uiState
    val cartItems = viewModel.cartItems
    val currentLanguage = viewModel.currentLanguage
    val cartSummary = viewModel.getCartSummary()
    
    // Show order success dialog
    if (uiState.orderSuccess) {
        OrderSuccessDialog(
            transactionId = uiState.transactionId ?: "",
            onDismiss = {
                viewModel.clearOrderSuccess()
                onBackClick()
            },
            currentLanguage = currentLanguage
        )
    }
    
    // Show loading screen
    if (uiState.isLoading) {
        LoadingScreen()
        return
    }
      Scaffold(
        topBar = {
            LargeTopAppBar(
                title = {
                    Text(
                        text = if (currentLanguage == "English") "Cart" else "कार्ट",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )                },
                navigationIcon = {
                    CompatFilledIconButton(
                        onClick = onBackClick,
                        containerColor = MaterialTheme.colorScheme.secondary,
                        contentColor = MaterialTheme.colorScheme.onSecondary
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        if (cartItems.isEmpty()) {
            EmptyCartScreen(
                onBackClick = onBackClick,
                currentLanguage = currentLanguage,
                modifier = Modifier.padding(paddingValues)
            )
        } else {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(20.dp)
                ) {
                    item {
                        Text(
                            text = if (currentLanguage == "English") 
                                "Your Order" 
                            else 
                                "आपका ऑर्डर",
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(bottom = 12.dp)
                        )
                    }
                    
                    items(cartSummary.items) { cartItem ->
                        CartItemCard(
                            cartItem = cartItem,
                            onAddToCart = { 
                                // Find the original item and add to cart
                                val cuisines = viewModel.uiState.cuisines
                                val item = cuisines
                                    .flatMap { it.items }
                                    .find { it.id == cartItem.itemId }
                                if (item != null) {
                                    viewModel.addToCart(cartItem.cuisineId, item)
                                }
                            },
                            onRemoveFromCart = { 
                                viewModel.removeFromCart(cartItem.itemId) 
                            }
                        )
                    }
                }
                
                // Cart Summary
                CartSummaryCard(
                    cartSummary = cartSummary,
                    onPlaceOrder = { viewModel.placeOrder() },
                    currentLanguage = currentLanguage,
                    isLoading = uiState.isLoading
                )
            }
        }
    }
}

@Composable
private fun CartItemCard(
    cartItem: CartItem,
    onAddToCart: () -> Unit,
    onRemoveFromCart: () -> Unit
) {
    val item = Item(
        id = cartItem.itemId,
        name = cartItem.name,
        imageUrl = cartItem.imageUrl,
        price = cartItem.price.toString(),
        rating = "0.0"
    )
    
    DishCard(
        item = item,
        quantity = cartItem.quantity,
        onAddToCart = onAddToCart,
        onRemoveFromCart = onRemoveFromCart
    )
}

@Composable
private fun CartSummaryCard(
    cartSummary: CartSummary,
    onPlaceOrder: () -> Unit,
    currentLanguage: String,
    isLoading: Boolean
) {    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 12.dp)
    ) {
        Column(
            modifier = Modifier.padding(24.dp)
        ) {
            Text(
                text = if (currentLanguage == "English") "Order Summary" else "ऑर्डर सारांश",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 16.dp)
            )
              // Subtotal
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = if (currentLanguage == "English") "Subtotal" else "उप-योग",
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = "₹${String.format("%.2f", cartSummary.subtotal)}",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // CGST
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "CGST (2.5%)",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "₹${String.format("%.2f", cartSummary.cgst)}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            Spacer(modifier = Modifier.height(4.dp))
            
            // SGST
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "SGST (2.5%)",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "₹${String.format("%.2f", cartSummary.sgst)}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }            
            HorizontalDivider(
                modifier = Modifier.padding(vertical = 16.dp),
                thickness = 2.dp,
                color = MaterialTheme.colorScheme.outline
            )
            
            // Total
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = if (currentLanguage == "English") "Total" else "कुल",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "₹${String.format("%.2f", cartSummary.total)}",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            
            FilledTonalButton(
                onClick = onPlaceOrder,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp)
                    .height(56.dp),
                enabled = !isLoading,
                shape = RoundedCornerShape(16.dp)
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        strokeWidth = 3.dp,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                } else {
                    Text(
                        text = if (currentLanguage == "English") "Place Order" else "ऑर्डर दें",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
private fun EmptyCartScreen(
    onBackClick: () -> Unit,
    currentLanguage: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(40.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            ),
            shape = RoundedCornerShape(24.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier.padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = if (currentLanguage == "English") "Your cart is empty" else "आपका कार्ट खाली है",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.primary
                )
                
                Text(
                    text = if (currentLanguage == "English") 
                        "Add some delicious items to get started!" 
                    else 
                        "शुरुआत करने के लिए कुछ स्वादिष्ट आइटम जोड़ें!",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 12.dp, bottom = 32.dp)
                )
                
                FilledTonalButton(
                    onClick = onBackClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(
                        text = if (currentLanguage == "English") "Continue Shopping" else "खरीदारी जारी रखें",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
private fun OrderSuccessDialog(
    transactionId: String,
    onDismiss: () -> Unit,
    currentLanguage: String
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        icon = {
            Icon(
                imageVector = Icons.Default.CheckCircle,
                contentDescription = "Success",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(56.dp)
            )
        },
        title = {
            Text(
                text = if (currentLanguage == "English") "Order Placed!" else "ऑर्डर दिया गया!",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        },
        text = {
            Column {
                Text(
                    text = if (currentLanguage == "English") 
                        "Your order has been placed successfully!" 
                    else 
                        "आपका ऑर्डर सफलतापूर्वक दिया गया है!",
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center
                )
                
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = if (currentLanguage == "English") 
                            "Transaction ID: $transactionId" 
                        else 
                            "लेनदेन आईडी: $transactionId",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(12.dp)
                    )
                }
            }
        },
        confirmButton = {
            FilledTonalButton(
                onClick = onDismiss,
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = if (currentLanguage == "English") "OK" else "ठीक है",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }
        },
        shape = RoundedCornerShape(24.dp)
    )
}

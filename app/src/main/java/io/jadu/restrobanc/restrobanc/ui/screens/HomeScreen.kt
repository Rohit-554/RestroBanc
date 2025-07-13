package io.jadu.restrobanc.restrobanc.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import io.jadu.restrobanc.restrobanc.data.model.CartItem
import io.jadu.restrobanc.restrobanc.data.model.Cuisine
import io.jadu.restrobanc.restrobanc.data.model.Item
import io.jadu.restrobanc.restrobanc.ui.components.CompatFilledIconButton
import io.jadu.restrobanc.restrobanc.ui.components.CuisineCard
import io.jadu.restrobanc.restrobanc.ui.components.DishCard
import io.jadu.restrobanc.restrobanc.ui.components.ErrorScreen
import io.jadu.restrobanc.restrobanc.ui.components.LoadingScreen
import io.jadu.restrobanc.restrobanc.ui.viewmodel.RestaurantViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: RestaurantViewModel,
    onCuisineClick: (String) -> Unit,
    onCartClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState = viewModel.uiState
    val cartItems = viewModel.cartItems
    val currentLanguage = viewModel.currentLanguage

    if (uiState.isLoading) {
        LoadingScreen()
        return
    }
    //Api outputs wrong result - enable when fixed
    /*if (uiState.error != null) {
        ErrorScreen(
            error = uiState.error,
            onRetry = { viewModel.refreshData() }
        )
        return
    }*/
      Scaffold(
        topBar = {
            LargeTopAppBar(
                title = {
                    Text(
                        text = if (currentLanguage == "English") "Restaurant" else "रेस्टोरेंट",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )
                },                actions = {
                    // Language Toggle Button
                    CompatFilledIconButton(
                        onClick = { viewModel.switchLanguage() },
                        containerColor = MaterialTheme.colorScheme.secondary,
                        contentColor = MaterialTheme.colorScheme.onSecondary
                    ) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "Switch Language"
                        )
                    }
                    
                    Spacer(modifier = Modifier.width(8.dp))
                      // Cart Button with Badge
                    Box {
                        CompatFilledIconButton(
                            onClick = onCartClick,
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        ) {
                            Icon(
                                imageVector = Icons.Default.ShoppingCart,
                                contentDescription = "Cart"
                            )
                        }
                        
                        val totalItems = cartItems.sumOf { it.quantity }
                        if (totalItems > 0) {
                            Box(
                                modifier = Modifier
                                    .size(20.dp)
                                    .background(
                                        MaterialTheme.colorScheme.error,
                                        CircleShape
                                    )
                                    .align(Alignment.TopEnd),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = totalItems.toString(),
                                    color = MaterialTheme.colorScheme.onError,
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                    
                    Spacer(modifier = Modifier.width(16.dp))
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Cuisine Categories Section
            item {
                CuisineSection(
                    cuisines = uiState.cuisines,
                    onCuisineClick = onCuisineClick,
                    currentLanguage = currentLanguage
                )
            }
            
            // Top Dishes Section
            item {
                TopDishesSection(
                    topDishes = uiState.topDishes,
                    cartItems = cartItems,
                    onAddToCart = { item ->
                        // Find cuisine for this item
                        val cuisine = uiState.cuisines.find { cuisine ->
                            cuisine.items.any { it.id == item.id }
                        }
                        if (cuisine != null) {
                            viewModel.addToCart(cuisine.cuisineId, item)
                        }
                    },
                    onRemoveFromCart = { item ->
                        viewModel.removeFromCart(item.id)
                    },
                    currentLanguage = currentLanguage
                )
            }
        }
    }
}

@Composable
private fun CuisineSection(
    cuisines: List<Cuisine>,
    onCuisineClick: (String) -> Unit,
    currentLanguage: String
) {
    Column(
        modifier = Modifier.padding(vertical = 16.dp)
    ) {
        Text(
            text = if (currentLanguage == "English") "Cuisines" else "व्यंजन",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Normal,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
        )
        
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            items(cuisines) { cuisine ->
                CuisineCard(
                    cuisine = cuisine,
                    onClick = { onCuisineClick(cuisine.cuisineId) }
                )
            }
        }
    }
}

@Composable
private fun TopDishesSection(
    topDishes: List<Item>,
    cartItems: List<CartItem>,
    onAddToCart: (Item) -> Unit,
    onRemoveFromCart: (Item) -> Unit,
    currentLanguage: String
) {
    Column(
        modifier = Modifier.padding(vertical = 16.dp)
    ) {
        Text(
            text = if (currentLanguage == "English") "Top Dishes" else "शीर्ष व्यंजन",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Normal,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
        )
        
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            topDishes.forEach { dish ->
                val cartItem = cartItems.find { it.itemId == dish.id }
                val quantity = cartItem?.quantity ?: 0
                
                DishCard(
                    item = dish,
                    quantity = quantity,
                    onAddToCart = { onAddToCart(dish) },
                    onRemoveFromCart = { onRemoveFromCart(dish) },
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
        }
    }
}

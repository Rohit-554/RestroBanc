# Restro App

This is a native Android app built as a technical assignment for OneBanc. It is a food ordering application that displays cuisines, dishes, and allows users to place orders using a provided API contract.

---

## Demo

[restroApp.webm](https://github.com/user-attachments/assets/b25aab64-f0ff-4b4b-bb96-5cef0d49b6f0)

## ✅ Features

- Built entirely in **Jetpack Compose**
- Clean **MVVM architecture**
- Displays a list of cuisine categories in horizontal scroll
- Displays **Top 3 Famous Dishes**
- Allows adding items with quantity
- Displays cart with:
  - Selected items
  - Net total
  - CGST (2.5%) and SGST (2.5%)
  - Grand Total
- Supports placing order via API (`make_payment`)
- **Language Toggle**: Switch between **Hindi and English**
- No third-party libraries used (except OkHttp & kotlinx serialization if allowed)

---

## 🛠️ Technologies Used

- **Kotlin**
- **Jetpack Compose**
- **ViewModel** and **StateFlow**
- **HttpURLConnection** / OkHttp (for API calls)
- **Kotlinx Serialization** (for JSON parsing)
- **No third-party UI or architecture libraries**

---

## 📱 Screens

### 1️⃣ Home Screen
- Horizontal scrollable **Cuisine Cards**
- Top 3 dishes displayed in tiles
- "Cart" and "Language" toggle buttons

### 2️⃣ Cuisine Detail Screen
- Shows all dishes of the selected cuisine
- User can add items to cart with quantity

### 3️⃣ Cart Screen
- Shows added items grouped by cuisine
- Displays:
  - Net total
  - CGST + SGST (2.5% each)
  - Grand total
- "Place Order" button integrates with `make_payment` API

---

## 🔗 API Endpoints Used

All API calls use the base URL:  
**`https://uat.onebanc.ai`**

| API                 | Endpoint                                | Purpose                      |
|---------------------|------------------------------------------|-------------------------------|
| `get_item_list`     | `/emulator/interview/get_item_list`      | Fetch cuisine categories and dishes |
| `get_item_by_id`    | `/emulator/interview/get_item_by_id`     | Fetch dish details by ID     |
| `get_item_by_filter`| `/emulator/interview/get_item_by_filter` | Filter dishes (price/rating) |
| `make_payment`      | `/emulator/interview/make_payment`       | Submit cart and place order  |

---

## 📦 API Headers (All Requests)

```http
X-Partner-API-Key: uonebancservceemultrS3cg8RaL30
X-Forward-Proxy-Action: <action_name>
Content-Type: application/json

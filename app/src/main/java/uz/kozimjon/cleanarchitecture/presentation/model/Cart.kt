package uz.kozimjon.cleanarchitecture.presentation.model

data class Cart(
    val id: Int?,
    val name: String?,
    val description: String?,
    val image: String?,
    val net_price: Float?,
    val count: Int?
)

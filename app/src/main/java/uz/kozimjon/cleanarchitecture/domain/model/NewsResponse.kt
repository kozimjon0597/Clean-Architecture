package uz.kozimjon.cleanarchitecture.domain.model

data class NewsResponse(
    val status: String?,
    val total: Int?,
    val data: List<New>?
) {
    data class New(
        val id: Int?,
        val name: String?,
        val description: String?,
        val image: String?,
        val net_price: Float?
    )
}

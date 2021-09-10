package ru.strorin.testmobica.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseDTO(
    @SerialName("page")
    val page: Page
)

@Serializable
data class Page(
    @SerialName("cards")
    val cards: List<Card>
)

@Serializable
data class Card(
    @SerialName("card_type")
    val card_type: String,
    @SerialName("card")
    val card: CardInner,
)

@Serializable
data class CardInner(
    @SerialName("value")
    val value: String,
    @SerialName("attributes")
    val attributes: Attributes,
)

@Serializable
data class Attributes(
    @SerialName("text_color")
    val text_color: String,
    @SerialName("font")
    val font: Font,
)

@Serializable
data class Font(
    @SerialName("size")
    val size: Int,
)
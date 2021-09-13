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
    val value: String? = null,
    @SerialName("attributes")
    val attributes: Attributes? = null,
    @SerialName("image")
    val image: Image? = null,
    @SerialName("title")
    val title: TextContent? = null,
    @SerialName("description")
    val description: TextContent? = null
)

@Serializable
data class Image(
    @SerialName("url")
    val url: String,
    @SerialName("size")
    val size: Size
)

@Serializable
data class Size(
        @SerialName("width")
        val width: Int,
        @SerialName("height")
        val height: Int
)

@Serializable
data class TextContent(
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
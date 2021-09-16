package ru.strorin.testmobica.ui

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.strorin.testmobica.R
import ru.strorin.testmobica.network.Attributes
import ru.strorin.testmobica.network.Card
import ru.strorin.testmobica.network.Image
import java.util.*

class ListAdapter: RecyclerView.Adapter<ListAdapter.CardViewHolder>() {

    private var list: List<Card> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val inflate = LayoutInflater.from(parent.context).inflate(R.layout.list_item_text, parent, false)
        return CardViewHolder(inflate)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val image = holder.itemView.findViewById<ImageView>(R.id.image)
        val description = holder.itemView.findViewById<TextView>(R.id.description)
        val title = holder.itemView.findViewById<TextView>(R.id.title)

        description.text = list[position].card.description?.value ?: ""
        list[position].card.description?.attributes?.let { setAttributesToTextView(description, it) }

        if (list[position].card_type.contains("title")) {
            title.text = list[position].card.title?.value ?: ""
            list[position].card.title?.attributes?.let { attrs -> setAttributesToTextView(title, attrs) }
        } else {
            title.text = list[position].card.value ?: ""
            list[position].card.attributes?.let { attrs -> setAttributesToTextView(title, attrs) }
        }

        list[position].card.image?.let { setImageToImageView(image, it) }
    }

    private fun setAttributesToTextView(textView: TextView, attributes: Attributes) {
        textView.setTextColor(Color.parseColor(attributes.text_color))
        textView.textSize = attributes.font.size.toFloat()
    }

    private fun setImageToImageView(view: ImageView, image: Image) {
        Glide.with(view.context)
                .load(image.url)
                .centerInside()
                .into(view);
    }

    override fun getItemCount(): Int = list.size

    override fun getItemViewType(position: Int): Int {
        val c = list[position]
        return when (c.card_type.toLowerCase(Locale.ROOT)) {
            "text" -> ViewType.text.ordinal
            "title_description" -> ViewType.title_description.ordinal
            "image_title_description" -> ViewType.image_description.ordinal
            else -> ViewType.text.ordinal
        }
    }

    fun setList(data: List<Card>) {
        list = data
        notifyDataSetChanged()
    }

    private enum class ViewType {
        text,
        title_description,
        image_title_description,
        image_description
    }

    class CardViewHolder(view: View): RecyclerView.ViewHolder(view) {

    }
}
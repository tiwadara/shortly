package com.tiwa.shortlyapp.ui.history

import android.content.Context
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.tiwa.common.data.model.ShortLinkData
import com.tiwa.shortlyapp.R
import kotlinx.android.synthetic.main.list_item_links.view.*

open class HistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

    fun bindItems(
        shortLinkData: ShortLinkData,
        delete: (Int, Int) -> Unit,
        copyUrl: (String) -> Unit,
        position:Int,
        context: Context
    ) {
        itemView.text_old_link.text = shortLinkData.original_link
        itemView.text_new_link.text = shortLinkData.short_link
        itemView.button_delete.setOnClickListener {
          delete.invoke(position,shortLinkData.id)
        }
        itemView.button_copy.setOnClickListener {
            itemView.button_copy.text = context.getString(R.string.copied)
            itemView.button_copy.setBackgroundColor(ContextCompat.getColor(context, R.color.primary))
            copyUrl.invoke(shortLinkData.full_short_link)
        }
    }

}
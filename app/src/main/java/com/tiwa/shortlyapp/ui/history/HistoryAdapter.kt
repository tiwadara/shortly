package com.tiwa.shortlyapp.ui.history

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tiwa.common.data.model.ShortLinkData
import com.tiwa.shortlyapp.R


class HistoryAdapter(
    private val shortLinkList: List<ShortLinkData>,
    private val delete: (Int, Int) -> Unit = { _: Int, _: Int -> },
    private val copyUrl: (String) -> Unit = {},
    private val context: Context
) : RecyclerView.Adapter<HistoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item_links, parent, false)
        return HistoryViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val shortLink = shortLinkList[position]
        holder.bindItems(shortLink,delete, copyUrl,position, context)
    }
    override fun getItemCount(): Int {
        return shortLinkList.size
    }

}
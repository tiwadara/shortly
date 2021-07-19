package com.tiwa.movies.ui.movies

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.tiwa.common.model.Movie
import kotlinx.android.synthetic.main.list_item_movies.view.*

open class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

    fun bindItems(movie: Movie, movieViewModel: MovieViewModel) {
        itemView.textViewFleetType.text = movie.title
        itemView.textViewOverview.text = movie.overview
        itemView.textViewPoiNumber.text = movie.id.toString()
        itemView.rootView.setOnClickListener {
            movieViewModel.loadMovie(movie.id)
        }
    }

}
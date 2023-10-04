package me.arwan.moviedb

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import me.arwan.moviedb.data.model.MovieResponse
import me.arwan.moviedb.databinding.ItemMovieBinding

class MovieAdapter(
    private val movieList: List<MovieResponse>, private val isLargeThumbnail: Boolean
) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    inner class MovieViewHolder(private val binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: MovieResponse) {
            Glide.with(binding.root.context)
                .load(movie.poster)
                .error(R.drawable.ic_broken_image)
                .into(binding.imageViewMovie)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        if (isLargeThumbnail) resizeView(binding.imageViewMovie)
        return MovieViewHolder(binding)
    }

    override fun getItemCount(): Int = movieList.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movieList[position])
    }

    private fun resizeView(view: View) =
        view.viewTreeObserver.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
            override fun onPreDraw(): Boolean {
                view.viewTreeObserver.removeOnPreDrawListener(this)
                val params = view.layoutParams
                params.width = view.measuredWidth * 2
                params.height = view.measuredHeight
                view.layoutParams = params
                return true
            }
        })

}
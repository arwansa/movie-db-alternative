package me.arwan.moviedb.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import me.arwan.moviedb.data.model.Section
import me.arwan.moviedb.databinding.ItemHeaderBinding
import me.arwan.moviedb.databinding.ItemMovieSectionBinding

class SectionAdapter(
    private val callback: MovieItemCallback
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var sectionList: List<Section> = emptyList()

    fun setSectionList(sectionList: List<Section>) {
        this.sectionList = sectionList
        notifyItemRangeInserted(1, sectionList.size)
    }

    inner class HeaderViewHolder(binding: ItemHeaderBinding) :
        RecyclerView.ViewHolder(binding.root)

    inner class SectionViewHolder(private val binding: ItemMovieSectionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(section: Section) {
            binding.textviewSectionTitle.text = section.title
            binding.recyclerView.adapter =
                MovieAdapter(section.movieList, section.isLargeThumbnail, callback)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) TYPE_HEADER else TYPE_ITEM
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_HEADER -> {
                val binding = ItemHeaderBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
                binding.imageViewProfile.setOnClickListener { callback.onProfileClicked() }
                HeaderViewHolder(binding)
            }

            else -> {
                val binding = ItemMovieSectionBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
                SectionViewHolder(binding)
            }
        }
    }

    override fun getItemCount(): Int = sectionList.size + 1

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is SectionViewHolder) {
            holder.bind(sectionList[position - 1])
        }
    }

    companion object {
        const val TYPE_HEADER = 0
        const val TYPE_ITEM = 1
    }

}
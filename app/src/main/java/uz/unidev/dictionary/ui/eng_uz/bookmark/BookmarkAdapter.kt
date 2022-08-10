package uz.unidev.dictionary.ui.eng_uz.bookmark

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.unidev.dictionary.data.entity.WordEntity
import uz.unidev.dictionary.databinding.ItemEngWordBinding

/**
 *  Created by Nurlibay Koshkinbaev on 31/07/2022 01:31
 */

class BookmarkAdapter : RecyclerView.Adapter<BookmarkAdapter.BookmarkViewHolder>() {

    var data = mutableListOf<WordEntity>()

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(items: List<WordEntity>) {
        data.clear()
        data.addAll(items)
        notifyDataSetChanged()
    }

    inner class BookmarkViewHolder(private val binding: ItemEngWordBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(wordEntity: WordEntity) {
            binding.tvWord.text = wordEntity.english
            binding.tvType.text = wordEntity.type
            binding.ivVolume.setOnClickListener {
                iconVolumeClick.invoke(wordEntity)
            }
            binding.item.setOnClickListener {
                itemClick.invoke(wordEntity)
            }
        }
    }

    private var iconVolumeClick: (WordEntity) -> Unit = {}
    fun onVolumeIconClickListener(block: (WordEntity) -> Unit) {
        this.iconVolumeClick = block
    }

    private var itemClick: (WordEntity) -> Unit = {}
    fun onItemClickListener(block: (WordEntity) -> Unit) {
        this.itemClick = block
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookmarkViewHolder {
        return BookmarkViewHolder(
            ItemEngWordBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: BookmarkViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }
}
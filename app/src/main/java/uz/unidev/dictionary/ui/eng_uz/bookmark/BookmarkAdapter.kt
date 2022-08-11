package uz.unidev.dictionary.ui.eng_uz.bookmark

import android.annotation.SuppressLint
import android.database.Cursor
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import timber.log.Timber
import uz.unidev.dictionary.data.entity.WordEntity
import uz.unidev.dictionary.databinding.ItemEngWordBinding
import uz.unidev.dictionary.utils.Extensions.geWordData

/**
 *  Created by Nurlibay Koshkinbaev on 31/07/2022 01:31
 */

class BookmarkAdapter : RecyclerView.Adapter<BookmarkAdapter.BookmarkViewHolder>() {

    private var cursor: Cursor? = null

    @SuppressLint("NotifyDataSetChanged")
    fun submitCursor(cursor: Cursor) {
        this.cursor = cursor
        notifyDataSetChanged()
    }

    inner class BookmarkViewHolder(private val binding: ItemEngWordBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {

            cursor!!.moveToPosition(adapterPosition)
            val data = cursor!!.geWordData()

            binding.tvWord.text = data.english
            binding.tvType.text = data.type
            binding.ivVolume.setOnClickListener {
                cursor!!.moveToPosition(adapterPosition)
                iconVolumeClick.invoke(data)
            }
            binding.item.setOnClickListener {
                cursor!!.moveToPosition(adapterPosition)
                itemClick.invoke(data)
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
        holder.bind()
    }

    override fun getItemCount(): Int {
        val count = cursor?.count ?: 0
        Timber.d(count.toString())
        return count
    }
}
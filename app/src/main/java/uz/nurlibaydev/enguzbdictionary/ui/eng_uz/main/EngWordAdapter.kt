package uz.nurlibaydev.enguzbdictionary.ui.eng_uz.main

import android.annotation.SuppressLint
import android.database.Cursor
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import timber.log.Timber
import uz.nurlibaydev.enguzbdictionary.data.entity.WordEntity
import uz.nurlibaydev.enguzbdictionary.databinding.ItemEngWordBinding
import uz.nurlibaydev.enguzbdictionary.utils.Extensions.coloredString
import uz.nurlibaydev.enguzbdictionary.utils.Extensions.geWordData

/**
 *  Created by Nurlibay Koshkinbaev on 31/07/2022 01:31
 */

class EngWordAdapter : RecyclerView.Adapter<EngWordAdapter.WordViewHolder>() {

    private var cursor: Cursor? = null
    // var data = mutableListOf<WordEntity>()
    var query: String? = null

    @SuppressLint("NotifyDataSetChanged")
    fun submitCursor(cursor: Cursor) {
        Timber.d("Submitted Cursor")
        this.cursor = cursor
        notifyDataSetChanged()
    }

    inner class WordViewHolder(private val binding: ItemEngWordBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {

            cursor!!.moveToPosition(adapterPosition)
            val data = cursor!!.geWordData()

            binding.tvWord.text = data.english.coloredString(query)
            binding.tvType.text = data.type

            binding.ivVolume.setOnClickListener {
                cursor!!.moveToPosition(adapterPosition)
                val data1 = cursor!!.geWordData()
                iconVolumeClick.invoke(data1)
            }

            binding.item.setOnClickListener {
                cursor!!.moveToPosition(adapterPosition)
                val data2 = cursor!!.geWordData()
                itemClick.invoke(data2)
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        return WordViewHolder(
            ItemEngWordBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int {
        val count = cursor?.count ?: 0
        Timber.d(count.toString())
        return count
    }
}
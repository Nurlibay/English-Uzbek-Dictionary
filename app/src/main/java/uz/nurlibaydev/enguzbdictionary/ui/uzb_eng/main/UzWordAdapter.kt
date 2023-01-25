package uz.nurlibaydev.enguzbdictionary.ui.uzb_eng.main

import android.annotation.SuppressLint
import android.database.Cursor
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import timber.log.Timber
import uz.nurlibaydev.enguzbdictionary.data.entity.WordEntity
import uz.nurlibaydev.enguzbdictionary.databinding.ItemUzWordBinding
import uz.nurlibaydev.enguzbdictionary.utils.Extensions.coloredString
import uz.nurlibaydev.enguzbdictionary.utils.Extensions.geWordData

/**
 *  Created by Nurlibay Koshkinbaev on 05/08/2022 15:33
 */

class UzWordAdapter: RecyclerView.Adapter<UzWordAdapter.WordViewHolder>() {

    private var cursor: Cursor? = null

    @SuppressLint("NotifyDataSetChanged")
    fun submitCursor(cursor: Cursor) {
        this.cursor = cursor
        notifyDataSetChanged()
    }

    var query: String? = null

    inner class WordViewHolder(private val binding: ItemUzWordBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {

            cursor!!.moveToPosition(adapterPosition)
            val data = cursor!!.geWordData()

            binding.tvWord.text = data.uzbek.coloredString(query)
            binding.item.setOnClickListener {
                cursor!!.moveToPosition(adapterPosition)
                val data1 = cursor!!.geWordData()
                itemClick.invoke(data1)
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
            ItemUzWordBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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
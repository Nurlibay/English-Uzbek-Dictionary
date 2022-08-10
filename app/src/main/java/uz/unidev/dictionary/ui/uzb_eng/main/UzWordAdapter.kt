package uz.unidev.dictionary.ui.uzb_eng.main

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.unidev.dictionary.data.entity.WordEntity
import uz.unidev.dictionary.databinding.ItemUzWordBinding
import uz.unidev.dictionary.utils.Extensions.coloredString

/**
 *  Created by Nurlibay Koshkinbaev on 05/08/2022 15:33
 */

class UzWordAdapter: RecyclerView.Adapter<UzWordAdapter.WordViewHolder>() {

    var data = mutableListOf<WordEntity>()

    var query: String? = null

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(items: List<WordEntity>) {
        data.clear()
        data.addAll(items)
        notifyDataSetChanged()
    }

    inner class WordViewHolder(private val binding: ItemUzWordBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(wordEntity: WordEntity) {
            binding.tvWord.text = wordEntity.uzbek.coloredString(query)
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        return WordViewHolder(
            ItemUzWordBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

}
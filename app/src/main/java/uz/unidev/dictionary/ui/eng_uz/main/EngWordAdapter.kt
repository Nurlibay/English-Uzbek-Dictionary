package uz.unidev.dictionary.ui.eng_uz.main

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import uz.unidev.dictionary.data.entity.WordEntity
import uz.unidev.dictionary.databinding.ItemEngWordBinding
import uz.unidev.dictionary.utils.Extensions.coloredString

/**
 *  Created by Nurlibay Koshkinbaev on 31/07/2022 01:31
 */

class EngWordAdapter : PagingDataAdapter<WordEntity, EngWordAdapter.WordViewHolder>(DiffUtilCallBack) {

    object DiffUtilCallBack : DiffUtil.ItemCallback<WordEntity>() {
        override fun areItemsTheSame(oldItem: WordEntity, newItem: WordEntity): Boolean {
            return oldItem.english == newItem.english
        }

        override fun areContentsTheSame(oldItem: WordEntity, newItem: WordEntity): Boolean {
            return oldItem.english == newItem.english &&
                    oldItem.uzbek == newItem.uzbek &&
                    oldItem.type == newItem.type
        }
    }

    var data = mutableListOf<WordEntity>()

    var query: String? = null

    @SuppressLint("NotifyDataSetChanged")
    fun submitDataList(items: List<WordEntity>) {
        data.clear()
        data.addAll(items)
        notifyDataSetChanged()
    }

    inner class WordViewHolder(private val binding: ItemEngWordBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(wordEntity: WordEntity) {
            binding.tvWord.text = wordEntity.english.coloredString(query)
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        return WordViewHolder(
            ItemEngWordBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }
}
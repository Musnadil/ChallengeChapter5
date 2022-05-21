package com.musnadil.challengechapter5.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.musnadil.challengechapter5.data.api.model.Article
import com.musnadil.challengechapter5.databinding.ItemBeritaBinding


class NewsAdapter(private val onItemClick: OnClickListener) :
    RecyclerView.Adapter<NewsAdapter.ViewHolder>() {
    private val diffCallBack = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }
    private val differ = AsyncListDiffer(this, diffCallBack)
    fun submitData(value: List<Article>?) = differ.submitList(value)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(ItemBeritaBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = differ.currentList[position]
        data.let {
            holder.bind(data)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
    inner class ViewHolder(private val binding: ItemBeritaBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Article) {
            binding.apply {
                Glide.with(binding.root)
                    .load(data.urlToImage)
                    .into(binding.imageNews)
                tvNewsTitle.text = data.title.toString()
                root.setOnClickListener {
                    onItemClick.onClickItem(data)
                }
            }

        }
    }
    interface OnClickListener {
        fun onClickItem(data: Article)
    }
}

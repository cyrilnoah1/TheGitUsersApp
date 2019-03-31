package com.rba.thegitusers.features.repository.repositories

import android.arch.paging.PagedListAdapter
import android.databinding.DataBindingUtil
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.rba.thegitusers.R
import com.rba.thegitusers.data.local.models.Repository
import com.rba.thegitusers.databinding.ItemRepositoryBinding

class RepositoriesAdapter(
    private val callback: ItemClickCallback? = null
) : PagedListAdapter<Repository, RepositoriesAdapter.RepoViewHolder>(DIFF) {

    override fun onCreateViewHolder(viewHolder: ViewGroup, viewType: Int): RepoViewHolder {
        return RepoViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(viewHolder.context),
                R.layout.item_repository,
                viewHolder,
                false
            )
        )
    }

    override fun onBindViewHolder(viewHolder: RepoViewHolder, position: Int) {
        getItem(position)?.let { viewHolder.bind(it) }
    }

    inner class RepoViewHolder(private val item: ItemRepositoryBinding) : RecyclerView.ViewHolder(item.root) {

        fun bind(repo: Repository) {
            item.repo = repo
            item.mcvRoot.setOnClickListener { callback?.onItemClick(repo) }
        }
    }

    interface ItemClickCallback {
        fun onItemClick(repo: Repository)
    }

    companion object {

        val DIFF = object : DiffUtil.ItemCallback<Repository>() {

            override fun areItemsTheSame(old: Repository, new: Repository) = old.id == new.id

            override fun areContentsTheSame(old: Repository, new: Repository) = old == new
        }
    }
}
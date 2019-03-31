package com.rba.thegitusers.features.contributor.contributor

import android.databinding.DataBindingUtil
import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.rba.thegitusers.R
import com.rba.thegitusers.data.local.models.Repository
import com.rba.thegitusers.databinding.ItemContribRepoLinkBinding

class ContributorRepoAdapter : ListAdapter<Repository, ContributorRepoAdapter.ContribRepoViewModel>(DIFF) {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ContribRepoViewModel {
        return ContribRepoViewModel(
            DataBindingUtil.inflate(LayoutInflater.from(p0.context), R.layout.item_contrib_repo_link, p0, false)
        )
    }

    override fun onBindViewHolder(p0: ContribRepoViewModel, p1: Int) {
        getItem(p1)?.let { p0.bind(it) }
    }

    inner class ContribRepoViewModel(private val item: ItemContribRepoLinkBinding) :
        RecyclerView.ViewHolder(item.root) {

        fun bind(repository: Repository) {
            item.link = repository.htmlUrl
        }
    }

    companion object {
        val DIFF = object : DiffUtil.ItemCallback<Repository>() {

            override fun areItemsTheSame(p0: Repository, p1: Repository) = p0.id == p1.id

            override fun areContentsTheSame(p0: Repository, p1: Repository) = p0 == p1
        }
    }
}
package com.rba.thegitusers.features.repository.repository

import android.databinding.DataBindingUtil
import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.rba.thegitusers.R
import com.rba.thegitusers.data.local.models.Contributor
import com.rba.thegitusers.databinding.ItemContributorBinding

class ContributorsAdapter(
    private val callback: OnItemClickListener? = null
) : ListAdapter<Contributor, ContributorsAdapter.ContribViewHolder>(DIFF) {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ContribViewHolder {
        return ContribViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(p0.context),
                R.layout.item_contributor,
                p0,
                false
            )
        )
    }

    override fun onBindViewHolder(p0: ContribViewHolder, p1: Int) {
        getItem(p1)?.let { p0.bind(it) }
    }

    inner class ContribViewHolder(private val item: ItemContributorBinding) : RecyclerView.ViewHolder(item.root) {

        fun bind(contributor: Contributor) {
            item.contrib = contributor
            item.clContributor.setOnClickListener { callback?.onItemClick(contributor) }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(contributor: Contributor)
    }

    companion object {

        val DIFF = object : DiffUtil.ItemCallback<Contributor>() {
            override fun areItemsTheSame(p0: Contributor, p1: Contributor) = p0.id == p1.id

            override fun areContentsTheSame(p0: Contributor, p1: Contributor) = p0 == p1
        }
    }
}
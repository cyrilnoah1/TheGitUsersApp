package com.rba.thegitusers.features.repository.repository


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController

import com.rba.thegitusers.R
import com.rba.thegitusers.common.BUNDLE_REPO_FULL_NAME
import com.rba.thegitusers.common.BUNDLE_REPO_HTML_LINK
import com.rba.thegitusers.common.BUNDLE_REPO_ID
import com.rba.thegitusers.common.EMPTY_STRING
import com.rba.thegitusers.data.local.models.Contributor
import com.rba.thegitusers.databinding.FragmentRepositoryBinding
import com.rba.thegitusers.features.HomeActivity
import kotlinx.android.synthetic.main.fragment_repository.*

class RepositoryFragment : Fragment(), ContributorsAdapter.OnItemClickListener {

    private var homeActivity: HomeActivity? = null
    private var binding: FragmentRepositoryBinding? = null
    private val adapter = ContributorsAdapter(this@RepositoryFragment)
    private val viewModel by lazy {
        val factory = RepositoryViewModel.Factory(requireActivity().application)
        ViewModelProviders.of(this, factory).get(RepositoryViewModel::class.java)
    }

    private var repoId: Int = -1
    private var repoFullName: String = EMPTY_STRING

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is HomeActivity) homeActivity = context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.run {
            if (containsKey(BUNDLE_REPO_ID)) repoId = getInt(BUNDLE_REPO_ID)
            if (containsKey(BUNDLE_REPO_FULL_NAME)) repoFullName = getString(BUNDLE_REPO_FULL_NAME, EMPTY_STRING)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_repository, container, false)
        binding?.lifecycleOwner = viewLifecycleOwner
        binding?.viewModel = viewModel
        binding?.title = getString(R.string.repository_title)

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getRepoDetails(repoId)
        viewModel.fetchContributors(repoFullName)

        tvLinkContent?.setOnClickListener {
            if (!tvLinkContent.text.isNullOrBlank()) {
                val link = tvLinkContent?.text?.toString()?.trim()
                link?.let {
                    val data = Bundle()
                    data.putString(BUNDLE_REPO_HTML_LINK, link)
                    findNavController().navigate(R.id.action_repositoryFragment_to_projectLinkFragment, data)
                }
            }
        }

        rvContributors?.run {
            layoutManager = GridLayoutManager(requireContext(), GRID_COUNT)
            adapter = this@RepositoryFragment.adapter
        }

        viewModel.obsContributors.observe(viewLifecycleOwner, Observer {
            it?.let { list -> adapter.submitList(list) }
        })

        viewModel.obsError.observe(viewLifecycleOwner, Observer {
            it?.let { error -> if (error.isNotBlank()) displayError(error) }
        })

        viewModel.obsIsDataLoading.observe(viewLifecycleOwner, Observer {
            it?.let { isLoading -> homeActivity?.toggleLoadingIconVisibility(isLoading) }
        })
    }

    override fun onItemClick(contributor: Contributor) {

    }

    private fun displayError(message: String): Toast {
        return Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT)
    }

    companion object {
        const val GRID_COUNT = 4
    }
}

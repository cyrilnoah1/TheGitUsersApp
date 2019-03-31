package com.rba.thegitusers.features.repository.repositories


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.rba.thegitusers.R
import com.rba.thegitusers.common.BUNDLE_REPO_FULL_NAME
import com.rba.thegitusers.common.BUNDLE_REPO_ID
import com.rba.thegitusers.data.local.models.Repository
import com.rba.thegitusers.features.HomeActivity
import kotlinx.android.synthetic.main.fragment_repositories.*
import kotlinx.android.synthetic.main.partial_home_toolbar.*

class RepositoriesFragment : Fragment(), RepositoriesAdapter.ItemClickCallback {

    private var homeActivity: HomeActivity? = null
    private var searchKey = DEFAULT_SEARCH_KEY
    private var adapter = RepositoriesAdapter(this@RepositoriesFragment)
    private val viewModel by lazy {
        val factory = RepositoriesViewModel.Factory(requireActivity().application)
        ViewModelProviders.of(this@RepositoriesFragment, factory).get(RepositoriesViewModel::class.java)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is HomeActivity) homeActivity = context
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_repositories, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Populating the list.
        rvRepositories?.run {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@RepositoriesFragment.adapter
        }
        // Setting up the list.
        setupRepositories()
        // Setting up search feature.
        setupSearch()

        // Handling error messages.
        viewModel.obsError.observe(viewLifecycleOwner, Observer {
            it?.let { error -> if (error.isNotBlank()) displayError(error).show() }
        })

        // Handling data progress.
        viewModel.obsIsDataLoading.observe(viewLifecycleOwner, Observer {
            it?.let { isLoading -> homeActivity?.toggleLoadingIconVisibility(isLoading) }
        })
    }

    override fun onItemClick(repo: Repository) {
        val data = Bundle()
        data.putInt(BUNDLE_REPO_ID, repo.id)
        data.putString(BUNDLE_REPO_FULL_NAME, repo.fullName)
        findNavController().navigate(R.id.action_repositoriesFragment_to_repositoryFragment, data)
    }

    private fun setupSearch() {
        etSearchBar?.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                if (!etSearchBar?.text.isNullOrBlank()) {
                    etSearchBar?.text?.toString()?.trim()?.run {
                        searchKey = this
                        setupRepositories()
                    }
                    true
                } else {
                    false
                }
            } else {
                false
            }
        }
    }

    private fun setupRepositories() {
        viewModel.fetchRepositories(searchKey)
        viewModel.getRepositoriesPaged(searchKey).observe(viewLifecycleOwner, Observer {
            it?.let { list ->
                if (list.any { l -> l.name == searchKey }) {
                    adapter.submitList(list)
                }
            }
        })
    }

    private fun displayError(message: String): Toast {
        return Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT)
    }

    companion object {
        const val DEFAULT_SEARCH_KEY = "android"
    }
}

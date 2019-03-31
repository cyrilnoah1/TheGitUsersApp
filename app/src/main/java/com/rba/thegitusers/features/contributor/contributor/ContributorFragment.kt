package com.rba.thegitusers.features.contributor.contributor


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.rba.thegitusers.databinding.FragmentContributorBinding


import com.rba.thegitusers.R
import com.rba.thegitusers.common.BUNDLE_CONTRIB_ID
import com.rba.thegitusers.common.BUNDLE_CONTRIB_LOGIN_NAME
import com.rba.thegitusers.common.EMPTY_STRING
import com.rba.thegitusers.common.setUrlSource
import com.rba.thegitusers.features.HomeActivity
import kotlinx.android.synthetic.main.fragment_contributor.*

class ContributorFragment : Fragment() {

    private var contribId: Int = -1
    private var contribLoginName = EMPTY_STRING
    private var homeActivity: HomeActivity? = null
    private val adapter = ContributorRepoAdapter()
    private var binding: FragmentContributorBinding? = null
    private val viewModel by lazy {
        val factory = ContributorViewModel.Factory(requireActivity().application)
        ViewModelProviders.of(this, factory).get(ContributorViewModel::class.java)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is HomeActivity) homeActivity = context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.run {
            if (containsKey(BUNDLE_CONTRIB_ID)) contribId = getInt(BUNDLE_CONTRIB_ID)
            if (containsKey(BUNDLE_CONTRIB_LOGIN_NAME)) contribLoginName =
                getString(BUNDLE_CONTRIB_LOGIN_NAME, EMPTY_STRING)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_contributor, container, false)
        binding?.viewModel = viewModel
        binding?.title = getString(R.string.contributor_title)

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.fetchUserRepos(contribLoginName, contribId)
        viewModel.getContributor(contribId)

        rvContributorRepos?.run {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@ContributorFragment.adapter
        }

        viewModel.obsContributor.observe(viewLifecycleOwner, Observer {
            it?.let { cont -> ivUserImage?.setUrlSource(cont.profilePicUrl) }
        })

        viewModel.obsRepos.observe(viewLifecycleOwner, Observer {
            it?.let { list -> adapter.submitList(list) }
        })

        // Handling error messages.
        viewModel.obsError.observe(viewLifecycleOwner, Observer {
            it?.let { error -> if (error.isNotBlank()) displayError(error).show() }
        })

        // Handling data progress.
        viewModel.obsIsDataLoading.observe(viewLifecycleOwner, Observer {
            it?.let { isLoading -> homeActivity?.toggleLoadingIconVisibility(isLoading) }
        })
    }

    private fun displayError(message: String): Toast {
        return Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT)
    }
}

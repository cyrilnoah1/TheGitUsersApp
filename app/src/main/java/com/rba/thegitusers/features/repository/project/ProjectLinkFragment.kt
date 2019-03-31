package com.rba.thegitusers.features.repository.project


import android.annotation.SuppressLint
import android.databinding.DataBindingUtil
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.*

import com.rba.thegitusers.R
import com.rba.thegitusers.common.BUNDLE_REPO_HTML_LINK
import com.rba.thegitusers.common.EMPTY_STRING
import com.rba.thegitusers.databinding.FragmentProjectLinkBinding
import kotlinx.android.synthetic.main.fragment_project_link.*

@SuppressLint("SetJavaScriptEnabled")
class ProjectLinkFragment : Fragment() {

    private var link: String = EMPTY_STRING
    private var binding: FragmentProjectLinkBinding? = null
    private val browser: WebView by lazy {

        wvProject.apply {
            settings.setAppCachePath(requireActivity().application.cacheDir.absolutePath)
            settings.allowFileAccess = true
            settings.setAppCacheEnabled(true)
            settings.javaScriptEnabled = true
            settings.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
            webViewClient = WebViewController()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.run {
            if (containsKey(BUNDLE_REPO_HTML_LINK)) link = getString(BUNDLE_REPO_HTML_LINK, EMPTY_STRING)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_project_link, container, false)
        binding?.title = getString(R.string.project_link)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        browser.loadUrl(link)
    }

    /**
     * Custom [WebViewClient] to load the page content in [wvNewsContent] instead of
     * external browsers.
     */
    inner class WebViewController : WebViewClient() {

        override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                Log.e(TAG, "${error?.description}")
            }
        }

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
        }
    }

    companion object {
        val TAG: String = ProjectLinkFragment::class.java.simpleName
    }
}

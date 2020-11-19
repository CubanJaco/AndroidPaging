package cu.jaco.androidpaging.ui.repo_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.map
import androidx.recyclerview.widget.RecyclerView
import cu.jaco.androidpaging.R
import cu.jaco.androidpaging.ui.base.BaseFragment
import cu.jaco.androidpaging.ui.repo_fragment.RepoPageAdapter
import cu.jaco.androidpaging.ui.repo_fragment.RepoViewModel
import cu.jaco.androidpaging.ui.repo_fragment.ReposLoadStateAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class RepoFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rv = view.findViewById<RecyclerView>(R.id.repo_rv)
        val progress = view.findViewById<ProgressBar>(R.id.progress_bar)
        val retry = view.findViewById<Button>(R.id.retry_button)

        val repoAdapter = RepoPageAdapter()
//        repoAdapter.addLoadStateListener { loadState ->
//            // Only show the list if refresh succeeds.
//            rv.isVisible = loadState.source.refresh is LoadState.NotLoading
//            // Show loading spinner during initial load or refresh.
//            progress.isVisible = loadState.source.refresh is LoadState.Loading
//            // Show the retry state if initial load or refresh fails.
//            retry.isVisible = loadState.source.refresh is LoadState.Error
//
//            // Toast on any error, regardless of whether it came from RemoteMediator or PagingSource
//            val errorState = loadState.source.append as? LoadState.Error
//                ?: loadState.source.prepend as? LoadState.Error
//                ?: loadState.append as? LoadState.Error
//                ?: loadState.prepend as? LoadState.Error
//
//            errorState?.let {
//                Toast.makeText(
//                    requireContext(),
//                    resources.getString(R.string.error, it.error),
//                    Toast.LENGTH_LONG
//                ).show()
//            }
//        }

        rv.adapter = repoAdapter.withLoadStateFooter(
            footer = ReposLoadStateAdapter { repoAdapter.retry() }
        )

        val viewModel = ViewModelProvider(this, modelFactory).get(RepoViewModel::class.java)

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getRepos().collectLatest {
                repoAdapter.submitData(it)
            }
        }

    }
}
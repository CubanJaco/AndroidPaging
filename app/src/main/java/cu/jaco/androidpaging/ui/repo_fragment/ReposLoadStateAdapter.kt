/*
 * Copyright (C) 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cu.jaco.androidpaging.ui.repo_fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import cu.jaco.androidpaging.R

class ReposLoadStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<ReposLoadStateAdapter.ReposLoadStateViewHolder>() {
    override fun onBindViewHolder(holder: ReposLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): ReposLoadStateViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.repos_load_state_footer_view_item, parent, false)
        return ReposLoadStateViewHolder(view, retry)
    }

    inner class ReposLoadStateViewHolder(
        itemVIew: View,
        retry: () -> Unit
    ) : RecyclerView.ViewHolder(itemVIew) {

        private val retryButton: Button = itemVIew.findViewById(R.id.retry_button)
        private val errorMsg: TextView = itemVIew.findViewById(R.id.error_msg)
        private val progressBar: ProgressBar = itemVIew.findViewById(R.id.progress_bar)

        init {
            retryButton.setOnClickListener { retry.invoke() }
        }

        fun bind(loadState: LoadState) {
            if (loadState is LoadState.Error)
                errorMsg.text = loadState.error.localizedMessage
            progressBar.isVisible = loadState is LoadState.Loading
            retryButton.isVisible = loadState !is LoadState.Loading
            errorMsg.isVisible = loadState !is LoadState.Loading
        }

    }

}

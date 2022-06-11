package com.ahimsarijalu.investin.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ahimsarijalu.investin.R
import com.ahimsarijalu.investin.data.datasource.remote.response.DataItem
import com.ahimsarijalu.investin.databinding.FragmentHomeBinding
import com.ahimsarijalu.investin.ui.details.DetailsActivity
import com.ahimsarijalu.investin.ui.post.PostActivity
import com.ahimsarijalu.investin.ui.settings.SettingsActivity
import com.ahimsarijalu.investin.utils.ViewModelFactory
import kotlinx.serialization.ExperimentalSerializationApi


@ExperimentalSerializationApi
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        setupViewModel()
        showExplore()
        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.home_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.btn_upload -> {
                Intent(activity, PostActivity::class.java).apply {
                    startActivity(this)
                }
                true
            }
            R.id.btn_settings -> {
                Intent(activity, SettingsActivity::class.java).apply {
                    startActivity(this)
                }
                true
            }
            else -> true
        }
    }

    private fun setupViewModel() {
        homeViewModel = ViewModelProvider(
            requireActivity(),
            ViewModelFactory(requireContext())
        )[HomeViewModel::class.java]

        val layoutManager = LinearLayoutManager(activity)
        binding.homeRv.layoutManager = layoutManager
    }

    private fun showExplore() {
        val adapter = HomeAdapter()
        binding.homeRv.adapter = adapter.withLoadStateFooter(
            footer = LoadingStateAdapter {
                adapter.retry()
            }
        )

        homeViewModel.explore.observe(viewLifecycleOwner) { explore ->
            adapter.submitData(lifecycle, explore)
        }

        adapter.setOnItemClickCallback(object : HomeAdapter.OnItemClickCallback {
            override fun onItemClicked(view: HomeAdapter.ListViewHolder, data: DataItem) {
                showSelectedExplore(data)
            }
        })
    }

    private fun showSelectedExplore(data: DataItem) {
        Intent(activity, DetailsActivity::class.java).apply {
            putExtra(DetailsActivity.EXTRA_USER, data)
            startActivity(this)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
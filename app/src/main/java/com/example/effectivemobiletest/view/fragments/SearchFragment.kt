package com.example.effectivemobiletest.view.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.effectivemobiletest.databinding.FragmentSearchBinding
import com.example.effectivemobiletest.view.adapters.SearchAdapter
import com.example.effectivemobiletest.viewModel.VacanciesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var searchAdapter: SearchAdapter

    private val viewModel: VacanciesViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initState()
    }

    private fun initState() {
        setRecyclers()
        setListeners()
        setObservers()
    }

    private fun setRecyclers() {
        searchAdapter = SearchAdapter(viewModel)
        binding.searchRecycler.apply {
            adapter = searchAdapter
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            itemAnimator = DefaultItemAnimator()
            setHasFixedSize(true)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setObservers() {

        viewModel.displayableItems.observe(viewLifecycleOwner) {
            searchAdapter.items = it
            searchAdapter.notifyDataSetChanged()
        }

        viewModel.more.observe(viewLifecycleOwner) {
            binding.searchPanelMore.root.visibility = if (it) View.VISIBLE else View.GONE
        }

        viewModel.allVacancies.observe(viewLifecycleOwner) {
            viewModel.setMode()
        }
    }

    private fun setListeners() {
        binding.searchPanelMore.backButton.setOnClickListener { viewModel.setDefaultMode() }
    }
}
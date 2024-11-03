package com.example.effectivemobiletest.view.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.effectivemobiletest.databinding.FragmentFavoritesBinding
import com.example.effectivemobiletest.view.adapters.SearchAdapter
import com.example.effectivemobiletest.viewModel.VacanciesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesFragment : Fragment() {

    private lateinit var binding: FragmentFavoritesBinding
    private lateinit var searchAdapter: SearchAdapter

    private val viewModel: VacanciesViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initState()
    }

    private fun initState() {
        setRecyclers()
        setObservers()
    }

    private fun setRecyclers() {
        searchAdapter = SearchAdapter(viewModel)
        binding.favoriteRecycler.apply {
            adapter = searchAdapter
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            itemAnimator = DefaultItemAnimator()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setObservers() {

        viewModel.favoriteVacancies.observe(viewLifecycleOwner) {
            viewModel.setFavoriteDisplayed()
        }

        viewModel.favoriteDisplayed.observe(viewLifecycleOwner) {
            Log.d("Favorite Displ", it.toString())
            searchAdapter.items = it
            searchAdapter.notifyDataSetChanged()
        }
    }

}
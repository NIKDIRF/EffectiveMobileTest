package com.example.effectivemobiletest.view.adapters

import com.example.data.model.DisplayableItem
import com.example.effectivemobiletest.viewModel.VacanciesViewModel
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter

class SearchAdapter(viewModel: VacanciesViewModel) : ListDelegationAdapter<List<DisplayableItem>> (
    topSearchPanelAdapterDelegate(),
    filterPanelAdapterDelegate(),
    offersRecyclerAdapterDelegate(),
    vacancyAdapterDelegate(viewModel),
    buttonAdapterDelegate(viewModel),
    favoriteCountTextAdapterDelegate()
)

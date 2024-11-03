package com.example.effectivemobiletest.view.adapters

import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.data.db.VacancyEntity
import com.example.data.model.ButtonItem
import com.example.data.model.DisplayableItem
import com.example.data.model.FavoriteCountItem
import com.example.data.model.FilterPanelItem
import com.example.data.model.OffersRecyclerItem
import com.example.data.model.TopSearchPanelItem
import com.example.effectivemobiletest.R
import com.example.effectivemobiletest.databinding.ItemButtonBinding
import com.example.effectivemobiletest.databinding.ItemFavoriteCountTextBinding
import com.example.effectivemobiletest.databinding.ItemFilterPanelBinding
import com.example.effectivemobiletest.databinding.ItemOffersRecyclerBinding
import com.example.effectivemobiletest.databinding.ItemTopSearchPanelBinding
import com.example.effectivemobiletest.databinding.ItemVacancyBinding
import com.example.effectivemobiletest.tools.NumberDeclensionHelper
import com.example.effectivemobiletest.viewModel.VacanciesViewModel
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding

fun vacancyAdapterDelegate(viewModel: VacanciesViewModel) = adapterDelegateViewBinding<VacancyEntity, DisplayableItem, ItemVacancyBinding>(
    { layoutInflater, root -> ItemVacancyBinding.inflate(layoutInflater, root, false) }
) {
    bind {
        val helper = NumberDeclensionHelper()
        binding.lookingNumberText.text = "Ещё ${helper.getPersonCountText(item.lookingNumber)}"
        binding.favoriteIcon.setImageResource(if (item.isFavorite) R.drawable.ic_menu_favorites_blue else R.drawable.ic_menu_favorites)
        binding.titleText.text = item.title
        binding.townText.text = item.town
        binding.experienceText.text = item.experiencePreview
        binding.publishedDateText.text = helper.formatPublicationDate(item.publishedDate)
        Log.d("Vacancy", item.title)

        binding.favoriteIcon.setOnClickListener {
            binding.favoriteIcon.setImageResource(if (item.isFavorite) R.drawable.ic_menu_favorites else R.drawable.ic_menu_favorites_blue)
            viewModel.updateFavorite(item)
        }
    }
}

fun offersRecyclerAdapterDelegate() = adapterDelegateViewBinding<OffersRecyclerItem, DisplayableItem, ItemOffersRecyclerBinding>(
    { layoutInflater, root -> ItemOffersRecyclerBinding.inflate(layoutInflater, root, false) }
) {
    bind {
        val offerAdapter = OfferAdapter()
        binding.offersRecycler.apply {
            layoutManager = LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
            adapter = offerAdapter
        }
        offerAdapter.refresh(item.offers)
        Log.d("SHOW DATA", "offersRecyclerAdapterDelegate bind")
    }
}

fun buttonAdapterDelegate(viewModel: VacanciesViewModel) = adapterDelegateViewBinding<ButtonItem, DisplayableItem, ItemButtonBinding>(
    { layoutInflater, root -> ItemButtonBinding.inflate(layoutInflater, root, false) }
) {
    bind {
        val helper = NumberDeclensionHelper()
        binding.buttonMore.text = helper.getVacancyCountText(item.count)
        binding.buttonMore.setOnClickListener { viewModel.setMoreMode() }
    }
}

fun topSearchPanelAdapterDelegate() = adapterDelegateViewBinding<TopSearchPanelItem, DisplayableItem, ItemTopSearchPanelBinding>(
    { layoutInflater, root -> ItemTopSearchPanelBinding.inflate(layoutInflater, root, false) }
) {
    bind {}
}

fun favoriteCountTextAdapterDelegate() = adapterDelegateViewBinding<FavoriteCountItem, DisplayableItem, ItemFavoriteCountTextBinding>(
    { layoutInflater, root -> ItemFavoriteCountTextBinding.inflate(layoutInflater, root, false) }
) {
    bind {
        val helper = NumberDeclensionHelper()
        binding.favoriteCountText.text = helper.getVacancyCountText(item.count)
    }
}

fun filterPanelAdapterDelegate() = adapterDelegateViewBinding<FilterPanelItem, DisplayableItem, ItemFilterPanelBinding>(
    { layoutInflater, root -> ItemFilterPanelBinding.inflate(layoutInflater, root, false) }
) {
    bind {
        val helper = NumberDeclensionHelper()
        binding.counterText.text = helper.getVacancyCountText(item.counter)
    }
}
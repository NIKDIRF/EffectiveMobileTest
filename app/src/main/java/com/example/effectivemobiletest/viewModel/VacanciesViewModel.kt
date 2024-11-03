package com.example.effectivemobiletest.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.db.VacancyEntity
import com.example.data.db.VacancyRepository
import com.example.data.dto.VacanciesResponse
import com.example.data.model.ButtonItem
import com.example.data.model.DisplayableItem
import com.example.data.model.FavoriteCountItem
import com.example.data.model.FilterPanelItem
import com.example.data.model.OffersRecyclerItem
import com.example.data.model.TopSearchPanelItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VacanciesViewModel @Inject constructor(
    private val apiService: com.example.api.VacanciesApi,
    private val repository: VacancyRepository
) : ViewModel() {

    val allVacancies: LiveData<List<VacancyEntity>> = repository.allVacancies
    val favoriteVacancies: LiveData<List<VacancyEntity>> = repository.favoriteVacancies
    val favoriteVacanciesCount: LiveData<Int> = repository.getFavoriteVacanciesCount()
    val more = MutableLiveData(false)

    private val _displayedItems = MutableLiveData<List<DisplayableItem>>()
    val displayableItems: LiveData<List<DisplayableItem>> = _displayedItems

    private val _favoriteDisplayed = MutableLiveData<List<DisplayableItem>>()
    val favoriteDisplayed: LiveData<List<DisplayableItem>> = _favoriteDisplayed

    private val _data = MutableLiveData<VacanciesResponse?>()
    val data: LiveData<VacanciesResponse?> get() = _data


    fun fetchData() {
        viewModelScope.launch {
            try {
                val response = apiService.getVacancies()
                if (response.isSuccessful) {
                    response.body()?.let { apiData ->
                        _data.value = apiData
                        repository.addVacancies(apiData.vacancies)
                        setDefaultMode()
                    }
                } else {
                    Log.e("VacanciesViewModel", "Ошибка запроса: ${response.errorBody()}")
                }
            } catch (e: Exception) {
                Log.e("VacanciesViewModel", "Ошибка сети: ${e.message}")
            }
        }
    }

    fun setDefaultMode() {
        viewModelScope.launch {
            val displayableItems = ArrayList<DisplayableItem>()
            displayableItems.add(TopSearchPanelItem(""))
            Log.d("Offers", "${_data.value?.offers.toString()}")
            displayableItems.add(OffersRecyclerItem(_data.value?.offers ?: listOf()))
            allVacancies.value?.take(3)?.forEach { displayableItems.add(it) }
            displayableItems.add(ButtonItem(_data.value?.vacancies?.size ?: 0))
            _displayedItems.value = displayableItems
            more.value = false
        }
    }

    fun setMoreMode() {
        viewModelScope.launch {
            val displayableItems = ArrayList<DisplayableItem>()
            displayableItems.add(FilterPanelItem(_data.value?.vacancies?.size ?: 0))
            allVacancies.value?.forEach { displayableItems.add(it) }
            _displayedItems.value = displayableItems
            more.value = true
        }
    }

    fun setFavoriteDisplayed() {
        viewModelScope.launch {
            val displayableItems = ArrayList<DisplayableItem>()
            displayableItems.add(FavoriteCountItem(favoriteVacanciesCount.value ?: 0))
            favoriteVacancies.value?.forEach {
                displayableItems.add(it)
            }
            _favoriteDisplayed.value = displayableItems
        }
    }

    fun setMode() {
        if (more.value == true) setMoreMode() else setDefaultMode()
    }

    fun updateFavorite(vacancyEntity: VacancyEntity) {
        viewModelScope.launch {
            repository.updateVacancy(vacancyEntity)
        }
    }
}
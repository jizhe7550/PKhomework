package com.jizhe7550.myapplication.ui.pickcharm

import androidx.lifecycle.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jizhe7550.myapplication.api.core.ApiResult
import com.jizhe7550.myapplication.model.CharmModel
import com.jizhe7550.myapplication.repository.CharmsRepository
import com.jizhe7550.myapplication.util.Preference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class PickCharmsViewModel(private val repository: CharmsRepository) : ViewModel(),
    LifecycleObserver {

    private val hasCache by Preference(Preference.Has_cache, "")

    private val _charmList: MutableLiveData<List<CharmModel>> = MutableLiveData()
    val charms: LiveData<List<CharmModel>>
        get() = _charmList


    fun getCharmList() {
        charmsDataFromCache()
        viewModelScope.launch(Dispatchers.Main) {
            val result = withContext(Dispatchers.Default) { repository.getCharms() }
            if (result is ApiResult.Success) {
                _charmList.value = result.data
            }
        }
    }

    private fun charmsDataFromCache() {
        if (hasCache != "") {
            _charmList.value =

                Gson().fromJson<ArrayList<CharmModel>>(
                    hasCache,
                    object : TypeToken<List<CharmModel>>() {}.type
                )
        }
    }

}
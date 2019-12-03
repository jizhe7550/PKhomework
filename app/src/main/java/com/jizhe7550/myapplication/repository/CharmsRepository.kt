package com.jizhe7550.myapplication.repository

import com.google.gson.Gson
import com.jizhe7550.myapplication.api.BaseRepository
import com.jizhe7550.myapplication.api.RetrofitClient
import com.jizhe7550.myapplication.api.core.ApiResult
import com.jizhe7550.myapplication.app.App
import com.jizhe7550.myapplication.util.Preference
import com.jizhe7550.myapplication.model.CharmModel


class CharmsRepository : BaseRepository() {

    private var hasCache by Preference(Preference.Has_cache, "")

    suspend fun getCharms(num: Int = 10): ApiResult<List<CharmModel>> {
        return safeApiCall(
            call = { requestCharms(num) },
            errorMessage = "No  Data!"
        )
    }

    // TODO Move into DataSource Layer ?
    private suspend fun requestCharms(num: Int = 10): ApiResult<List<CharmModel>> {
        val response = RetrofitClient.service.getCharms(num)
        hasCache = Gson().toJson(response)

        return executeResponse(response)
    }
}
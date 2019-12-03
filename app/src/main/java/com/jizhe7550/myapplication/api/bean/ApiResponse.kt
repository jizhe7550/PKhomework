package com.jizhe7550.myapplication.api.bean

data class ApiResponse<out T>(val errorCode: Int, val errorMsg: String, val data: T)
package com.jizhe7550.myapplication.model

import java.io.Serializable

data class CharmModel(
    val breeds: Any = "",
    val id: String = "",
    val url: String = "",
    val width: Int = 0,
    val height: Int = 0,
    val num: Int = 0
) : Serializable


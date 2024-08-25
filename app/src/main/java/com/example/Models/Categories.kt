package com.example.Models

import java.io.Serializable

data class Categories(
    var name: String? = "",
    var img: String? = "",
    var categoryId: String? = ""
) : Serializable {
    override fun toString(): String {
        return name ?: ""
    }
}
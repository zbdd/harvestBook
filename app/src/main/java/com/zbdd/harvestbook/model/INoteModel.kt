package com.zbdd.harvestbook.model

import java.time.LocalDateTime

interface INoteModel {
    val id: Int
    val title: String?
    val content: String?
    val dateTime: String?
    val updated: String?
}
package com.example.listdisplayapplication.helper

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class PostModel(
    @Id(assignable = true)
    var id: Long = 0, var title: String, var body: String
)
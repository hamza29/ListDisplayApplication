package com.example.listdisplayapplication.helper

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class PostModelLocalDb(
    @Id(assignable = true)
    var id: Long  , var title: String, var body: String
)
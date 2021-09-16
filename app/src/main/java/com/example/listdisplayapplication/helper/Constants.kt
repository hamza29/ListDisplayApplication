package com.helper

import com.application.BaseActivity


val URL = "https://jsonplaceholder.typicode.com"
enum class ApiAction(val value: String) {
    userPosts("posts")
}

var DAY_ID: Int = 0
var WEEK_ID: Int = 0
var TRAINER_ID: Int = 0
var PLAN_ID: Int = 0
var currentActiveActivityId: Int = 0
var currentActiveTrainerActivityId: Int = 0
var  checkis: Int = 0
var  sizeoflistis: Int = 0
var PREV_ACTIVITY: BaseActivity? = null


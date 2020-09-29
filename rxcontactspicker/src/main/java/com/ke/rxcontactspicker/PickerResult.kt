package com.ke.rxcontactspicker

data class PickerResult(
    val contacts: Contacts?,
    val errorMessage: String = ""
) {
    val success = contacts != null
}

data class Contacts(
    val name: String,
    val phone: String
)
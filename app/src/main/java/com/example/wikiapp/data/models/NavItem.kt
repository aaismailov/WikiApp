package com.example.wikiapp.data.models

import com.example.wikiapp.R

class NavItem {
    val i: String = ""
    val k: String = ""
    val l: String = ""
    val c: String = ""
    val y: String = ""
    val t: String = ""
}

fun NavItem.getIcon() = when(this.c) {
    "mdi-network-outline" -> R.drawable.network_outline
    "mdi-clipboard-check-multiple-outline" -> R.drawable.clipboard_check_multiple_outline
    "mdi-newspaper" -> R.drawable.newspaper
    "mdi-office-building" -> R.drawable.office_building
    "mdi-book-multiple" -> R.drawable.file_certificate
    "mdi-flask" -> R.drawable.flask
    "mdi-bank" -> R.drawable.bank
    "mdi-bookshelf" -> R.drawable.bookshelf
    "mdi-domain" -> R.drawable.domain
    "mdi-school" -> R.drawable.school
    "mdi-file-document" -> R.drawable.file_document
    "mdi-axis-arrow" -> R.drawable.axis_arrow
    "mdi-account-group" -> R.drawable.account_group
    "mdi-file-certificate" -> R.drawable.file_certificate
    "mdi-chart-bar" -> R.drawable.chart_bar
    "mdi-account-multiple" -> R.drawable.account_multiple
    "mdi-hammer-wrench" -> R.drawable.hammer_wrench
    "mdi-book-open-variant" -> R.drawable.network_outline
    else -> R.drawable.folder
}
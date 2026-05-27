package ru.netology.nmedia.functions
fun prnCount(count: Int): String =
    when {
        count < 1_000 -> count.toString()
        count < 10_000 -> "${((count / 100.0).toInt() / 10.0)}K"
        count < 1_000_000 -> "${count / 1_000}K"
        else -> "${((count / 100_000.0).toInt() / 10.0)}M"
    }

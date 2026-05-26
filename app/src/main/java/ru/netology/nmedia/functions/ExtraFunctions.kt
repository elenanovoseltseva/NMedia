package ru.netology.nmedia.functions

fun prnCount(count: Int): String {

    var strCount: String

    when {
        count < 1000 -> {
            strCount = count.toString()
        }

        count < 10_000 -> {
            val truncated = ((count / 1_000.0) * 10).toInt() / 10.0
            strCount = "${truncated}K"
        }

        count < 1_000_000 -> {
            strCount = String.format("%dK", count / 1_000)
        }

        else -> {
            val truncated = ((count / 1_000_000.0) * 10).toInt() / 10.0
            strCount = "${truncated}M"
        }
    }
    return strCount
}
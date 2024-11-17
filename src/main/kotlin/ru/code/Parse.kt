package ru.code

import ru.code.State.*

const val UPPER_CASE_LENGTH = 1
val lowercase = 'a'..'z'
val uppercase = 'A'..'Z'

fun MutableMap<String, Int>.parse(str: String) {
    val sb = StringBuilder()
    var state = OFF

    fun put(sb: StringBuilder) = with(sb) {
        if (this.length != 1)
            compute(this.toString()) { _, v -> v?.inc() ?: 1 }
        this.clear()
    }

    for (c in str) {
        when (c) {
            in lowercase -> {
                if (state == UPPER_CASE && sb.length != UPPER_CASE_LENGTH)
                    put(sb)
                sb.append(c)
                state = LOWER_CASE
            }

            in uppercase -> {
                if (state == LOWER_CASE)
                    put(sb)
                sb.append(c.lowercaseChar())
                state = UPPER_CASE
            }

            else -> {
                if (state != OFF) {
                    put(sb)
                    state = OFF
                }
            }
        }
    }
    if (state != OFF) put(sb)
}

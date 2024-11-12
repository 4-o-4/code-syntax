package ru.code

import ru.code.State.*

const val UPPER_CASE_LENGTH = 1
val lowercase = 'a'..'z'
val uppercase = 'A'..'Z'

fun parse(str: String) {
    val sb = StringBuilder()
    var state = OFF

    for (c in str) {
        when (c) {
            in lowercase -> {
                if (state == UPPER_CASE && sb.length != UPPER_CASE_LENGTH) {
                    println(sb)
                    sb.clear()
                }
                sb.append(c)
                state = LOWER_CASE
            }

            in uppercase -> {
                if (state == LOWER_CASE) {
                    println(sb)
                    sb.clear()
                }
                sb.append(c.lowercaseChar())
                state = UPPER_CASE
            }

            else -> {
                if (state != OFF) {
                    println(sb)
                    sb.clear()
                    state = OFF
                }
            }
        }
    }
    if (state != OFF) println(sb)
}

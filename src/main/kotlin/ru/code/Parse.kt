package ru.code

import ru.code.State.*
import java.io.File

const val UPPER_CASE_LENGTH = 1
val lowercase = 'a'..'z'
val uppercase = 'A'..'Z'

fun MutableMap<String, Int>.parse(str: String) {
    val sb = StringBuilder()
    var state = BREAK

    fun addWord() {
        if (sb.length != 1)
            this.compute(sb.toString()) { _, v -> v?.inc() ?: 1 }
        this.clear()
    }

    for (c in str) {
        when (c) {
            in lowercase -> {
                if (state == UPPER_CASE && sb.length != UPPER_CASE_LENGTH)
                    addWord()
                sb.append(c)
                state = LOWER_CASE
            }

            in uppercase -> {
                if (state == LOWER_CASE)
                    addWord()
                sb.append(c.lowercaseChar())
                state = UPPER_CASE
            }

            else -> {
                if (state == BREAK) {
                    if (c == '/' || c == '*' || c == '#')
                        break
                    if (c != ' ')
                        state = OFF_CASE
                } else if (state == LOWER_CASE || state == UPPER_CASE) {
                    addWord()
                    state = OFF_CASE
                }
            }
        }
    }
    if (state == LOWER_CASE || state == UPPER_CASE)
        addWord()
}

fun MutableMap<String, Int>.delete() {
    File("src/main/resources/delete_words.txt").bufferedReader().forEachLine {
        this.remove(it)
    }
}

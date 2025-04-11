package ru.code

import ru.code.State.*
import java.io.File

const val UPPER_CASE_LENGTH = 1

fun MutableMap<String, Int>.parse(str: String) {
    val sb = StringBuilder()
    var state = COMMENT

    fun addWord() {
        if (sb.length != 1) {
            this.compute(sb.toString()) { _, v -> v?.inc() ?: 1 }
        }
        sb.clear()
    }

    for (c in str) {
        when (c) {
            in 'a'..'z' -> {
                if (state == UPPER_CASE && sb.length != UPPER_CASE_LENGTH) {
                    addWord()
                }
                sb.append(c)
                state = LOWER_CASE
            }

            in 'A'..'Z' -> {
                if (state == LOWER_CASE) {
                    addWord()
                }
                sb.append(c.lowercaseChar())
                state = UPPER_CASE
            }

            else -> {
                if (state == COMMENT) {
                    if (c == '/' || c == '*' || c == '#') {
                        break
                    }
                    if (c != ' ') {
                        state = NOT_CASE
                    }
                } else if (state == LOWER_CASE || state == UPPER_CASE) {
                    addWord()
                    state = NOT_CASE
                }
            }
        }
    }
    if (state == LOWER_CASE || state == UPPER_CASE) {
        addWord()
    }
}

fun MutableMap<String, Int>.delete() {
    File("src/main/resources/delete_words.txt").bufferedReader().forEachLine {
        this.remove(it)
    }
}

private enum class State {
    LOWER_CASE,
    UPPER_CASE,
    NOT_CASE,
    COMMENT
}

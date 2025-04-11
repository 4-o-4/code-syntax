package ru.code

import java.io.File

fun main() {
    val map = mutableMapOf<String, Int>()
    println("Enter a path name:")
    val pathname = readln()

    File(pathname).bufferedReader().forEachLine {
        File(it).bufferedReader().forEachLine { str ->
            str.takeUnless {
                str.startsWith("package") || str.startsWith("import")
            }?.let { map.parse(str) }
        }
    }
    map.delete()
    map.forEach { (k, v) -> println("$k - $v") }
}

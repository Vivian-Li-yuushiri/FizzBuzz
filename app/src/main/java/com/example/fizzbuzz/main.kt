package com.example.fizzbuzz

fun fizzBuzz(n: Int): String {
    var result: String = ""
    if (!(n % 3 == 0) && !(n % 5 == 0)) {
        result += "$n"
    } else {
        if (n % 3 == 0) {
            result += "Fizz"
        }
        if (n % 5 == 0) {
            result += "Buzz"
        }
    }
    return result
}

fun main() {
    for (n in 1..101) {
        print(fizzBuzz(n))
    }
}
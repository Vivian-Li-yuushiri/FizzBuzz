package com.example.fizzbuzz

open class Rule(val m: Int) {
    fun checkRule(n: Int): Boolean {return (n % m == 0)}
}

class StringRule(val multiple: Int, val rule: String): Rule(multiple) {
    var insFun: (String) -> Int = {s: String -> s.length}
    fun setInsertFun(lmbda: (String) -> Int) {
        insFun = lmbda
    }
    fun applyRule(result: String): String {
        if (insFun(result) == -1) {return result + rule}
        return (result.substring(0, insFun(result)) + rule + result.substring(insFun(result)))
    }
}
class OrderRule(val multiple: Int): Rule(multiple) {
    var ordFun: (List<StringRule>) -> List<StringRule> = {rules: List<StringRule> -> rules}
    fun setOrderFun(lmbda: (List<StringRule>) -> List<StringRule>) {
        ordFun = lmbda
    }
    fun applyRule(rules: List<StringRule>): List<StringRule> {
        return ordFun(rules)
    }
}

fun fizzBuzz(n: Int, order: List<OrderRule>, rules: List<StringRule>): String {
    var result: String = ""
    var ruleList = rules

    for (rule in order) {
        if (rule.checkRule(n)){
            ruleList = rule.applyRule(rules)
        }
    }
    for (rule in ruleList) {
        if (rule.checkRule(n)) {
            result = rule.applyRule(result)
        }
    }

    return result
}

fun main(args: Array<String>) {
    val threeFizz = StringRule(3, "Fizz")
    val fiveBuzz = StringRule(5, "Buzz")
    val sevenBang = StringRule(7, "Bang")
    val elevenBong = StringRule(11, "Bong")
    val thirteenFezz = StringRule(13, "Fezz")
    thirteenFezz.setInsertFun{s: String -> s.indexOf("B")}

    val elevenOrder = OrderRule(11)
    elevenOrder.setOrderFun{rules: List<StringRule> -> listOf(elevenBong, thirteenFezz)}
    val seventeenOrder = OrderRule(17)
    seventeenOrder.setOrderFun{rules: List<StringRule> -> rules.reversed()}

    val numberLimit: Int = 0
    var rules =  mutableListOf<StringRule>()
    var order =  mutableListOf<OrderRule>()

    if (args.size > 1) {
        for (i in args.slice(1..args.size)) {
            when (i.toIntOrNull()) {
                3 -> {
                    rules.add(threeFizz)
                }
                5 -> {
                    rules.add(fiveBuzz)
                }
                7 -> {
                    rules.add(sevenBang)
                }
                11 -> {
                    rules.add(elevenBong)
                    order.add(elevenOrder)
                }
                13 -> {
                    rules.add(thirteenFezz)
                }
                17 -> {
                    order.add(seventeenOrder)
                }
            }
        }
    }

    for (n in 1..numberLimit) {
        println("$n: ${fizzBuzz(n, order, rules)}")
    }
}
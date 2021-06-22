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

fun main() {
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

    val rules = listOf(threeFizz, fiveBuzz, sevenBang, elevenBong, thirteenFezz)
    val order = listOf(elevenOrder, seventeenOrder)

    for (n in 1..101) {
        println("$n: ${fizzBuzz(n, order, rules)}")
    }
}
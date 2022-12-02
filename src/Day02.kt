import Hand.*
import kotlin.IllegalArgumentException

fun main() {
    fun part1(input: List<String>) = input.sumOf {
        it[2].toHand().value + it[2].toHand().result(it[0].toHand())
    }

    fun part2(input: List<String>) = input.sumOf {
        it[2].requiredHand(it[0].toHand()).value + it[2].resultToInt()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 15)
    check(part2(testInput) == 12)

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}

fun Char.resultToInt() = when {
    this == 'X' -> 0
    this == 'Y' -> 3
    this == 'Z' -> 6
    else -> throw IllegalArgumentException()
}

fun Char.toHand() = when {
    this == 'X' || this == 'A' -> R
    this == 'Y' || this == 'B' -> P
    this == 'Z' || this == 'C' -> S
    else -> throw IllegalArgumentException()
}

fun Char.requiredHand(opposition: Hand) = when {
    this == 'X' -> opposition.beats
    this == 'Y' -> opposition
    this == 'Z' -> opposition.loses
    else -> throw IllegalArgumentException()
}

enum class Hand(val value: Int) {
    R(1),
    P(2),
    S(3);

    fun result(opposition: Hand) = when {
        this.beats == opposition -> 6
        opposition.beats == this -> 0
        else -> 3
    }

    val beats: Hand
        get() = when (this) {
            R -> S
            P -> R
            S -> P
        }

    val loses: Hand
        get() = when (this) {
            R -> P
            P -> S
            S -> R
        }
}

import java.lang.Integer.*
import java.util.Stack

fun main() {
    data class Instruction(
        val num: Int,
        val from: Int,
        val to: Int
    )

    fun processInitialState(rawState: String): List<Stack<Char>> {
        val stackNum = rawState[rawState.lastIndex - 1].digitToInt()
        val state = List(stackNum) { Stack<Char>() }

        rawState
            .chunked(size = 4) // Separate into single entries in a stack
            .map { it[1] } // Extract out letter or empty whitespace
            .dropLast(stackNum) // Remove the labels below stacks
            .chunked(stackNum) // Level order the entries
            .reversed() // Start at the bottom of the stack levels
            .forEach { level ->
                level.forEachIndexed { i, c ->
                    if (c == ' ') return@forEachIndexed
                    state[i].push(c)
                }
            }
        return state
    }

    fun String.parseInstructions() = split("\n")
        .map { it.split(' ') }
        .map { rawInstruction ->
            Instruction(parseInt(rawInstruction[1]), parseInt(rawInstruction[3]) - 1, parseInt(rawInstruction[5]) - 1)
        }

    fun List<Stack<Char>>.process9000(instruction: Instruction) {
        val (num, from, to) = instruction
        repeat(num) { get(to).push(get(from).pop()) }
    }

    fun List<Stack<Char>>.process9001(instruction: Instruction) {
        val (num, from, to) = instruction
        val stack = Stack<Char>()

        repeat(num) { stack.push(get(from).pop()) }
        repeat(num) { get(to).push(stack.pop()) }
    }

    fun List<Stack<Char>>.peekTop() = map { it.peek() }.joinToString("")

    fun part1(input: String): String {
        val (rawState, rawInstructions) = input.split("\n\n")
        val state = processInitialState(rawState)

        rawInstructions.parseInstructions().forEach { state.process9000(it) }
        return state.peekTop()
    }

    fun part2(input: String): String {
        val (rawState, rawInstructions) = input.split("\n\n")
        val state = processInitialState(rawState)

        rawInstructions.parseInstructions().forEach { state.process9001(it) }
        return state.peekTop()
    }

    val testInput = readInputText("Day05_test")
    check(part1(testInput) == "CMZ")
    check(part2(testInput) == "MCD")

    val input = readInputText("Day05")
    println(part1(input))
    println(part2(input))
}
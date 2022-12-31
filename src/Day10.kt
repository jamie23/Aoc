fun main() {

    fun solution(input: List<String>): Int {
        val set = setOf(20, 60, 100, 140, 180, 220)
        val result = mutableListOf<Int>()

        var total = 1
        var instructionIndex = 0
        val instructions = input
            .map { it.split(' ') }
            .toInstructions()

        var currentInstruction = instructions[instructionIndex]

        for (i in 1..237) {
            if (i in set) result.add(total * i)

            // Part 2
            if (((i - 1) % 40) in total - 1..total + 1) {
                print("█")
            } else {
                print(".")
            }

            if (i % 40 == 0) println()

            if (--currentInstruction.cycles <= 0) {
                total += currentInstruction.value
                currentInstruction = instructions[++instructionIndex]
            }
        }

        return result.sum()
    }


    val testInput = readInput("Day10_test")
    check(solution(testInput) == 13140)

    val input = readInput("Day10")
    println(solution(input))
}

fun List<List<String>>.toInstructions(): List<Instruction> =
    map {
        if (it.size == 2) {
            val (a, b) = it
            a.parseInstruction(b.toInt())
        } else {
            it.first().parseInstruction()
        }
    }

fun String.parseInstruction(value: Int = 0) = when (this) {
    "addx" -> Instruction(cycles = 2, value)
    "noop" -> Instruction(cycles = 1)
    else -> error("Unsupported Instruction")
}

data class Instruction(
    var cycles: Int,
    val value: Int = 0
)

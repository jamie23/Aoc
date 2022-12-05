fun main() {
    fun part1(input: List<String>) = input
        .map { s ->
            listOf(
                s.substring(0, s.length / 2),
                s.substring(
                    s.length / 2, s.length
                )
            )
        }
        .map { it.commonChar() }
        .sumOf { it.calculatePriority() }

    fun part2(input: List<String>) = input
        .chunked(3)
        .map { it[0].toSet().intersect(it[1].toSet().intersect(it[2].toSet())) }
        .sumOf { it.single().calculatePriority() }

    val testInput = readInput("Day03_test")
    check(part1(testInput) == 157)
    check(part2(testInput) == 70)

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}

fun List<String>.commonChar(): Char {
    return first().toSet().intersect(this[1].toSet()).single()
}

fun Char.calculatePriority() = when (this) {
    in 'a'..'z' -> this - 'a' + 1
    in 'A'..'Z' -> this - 'A' + 27
    else -> error("Unsupported character")
}
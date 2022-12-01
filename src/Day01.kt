fun main() {
    fun listNums(input: String) = input
        .split("\n\n")
        .map { numString ->
            numString.split("\n").map { num -> num.toInt() }
        }.map { it.sum() }

    fun part1(input: String) =
        listNums(input).max()

    fun part2(input: String) =
        listNums(input).sortedDescending().take(3).sum()

    // test if implementation meets criteria from the description, like:
    val testInput = readInputText("Day01_test")
    check(part1(testInput) == 24000)

    val input = readInputText("Day01")
    println(part1(input))
    println(part2(input))
}

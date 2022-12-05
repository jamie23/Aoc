fun main() {
    data class Time(
        val start: Int,
        val end: Int
    )

    infix fun Time.overlaps(b: Time) = start <= b.start && end >= b.end
    infix fun Time.singleOverlap(b: Time) = b.start in start..end || b.end in start..end

    fun List<String>.mapToTimes() = this.map { line ->
        line.split(',')
            .map { range -> range.split("-").map { Integer.valueOf(it) } }
            .map { Time(it[0], it[1]) }
    }

    fun part1(input: List<String>) = input
        .mapToTimes()
        .map { (a, b) -> a overlaps b || b overlaps a }
        .count { it }

    fun part2(input: List<String>) = input
        .mapToTimes()
        .map { (a, b) -> a singleOverlap b || b singleOverlap a }
        .count { it }

    val testInput = readInput("Day04_test")
    check(part1(testInput) == 2)
    check(part2(testInput) == 4)

    val input = readInput("Day04")
    println(part1(input))
    println(part2(input))
}
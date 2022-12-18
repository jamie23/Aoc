fun main() {
    fun imperativeSlidingWindow(input: String, window: Int): Int {
        var start = 0
        var end = 1

        val set = hashSetOf<Char>()
        set.add(input[start])
        set.add(input[end])

        while (set.size != window) {
            end++
            if (set.contains(input[end])) {
                while (set.contains(input[end])) {
                    set.remove(input[start])
                    start++
                }
            }
            set.add(input[end])

        }
        return end + 1
    }

    fun String.functionalSlidingWindow(window: Int) =
        windowed(size = window).indexOfFirst { it.toSet().size == window } + window

    fun part1Imp(input: String) = imperativeSlidingWindow(input = input, window = 4)
    fun part1Fun(input: String) = input.functionalSlidingWindow(window = 4)
    fun part2Imp(input: String) = imperativeSlidingWindow(input = input, window = 14)
    fun part2Fun(input: String) = input.functionalSlidingWindow(window = 14)

    val testInput = readInputText("Day06_test")
    check(part1Imp(testInput) == 11)
    check(part1Fun(testInput) == 11)

    val input = readInputText("Day06")
    println(part1Imp(input))
    println(part1Fun(input))
    println(part2Imp(input))
    println(part2Fun(input))
}
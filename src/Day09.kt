import kotlin.math.absoluteValue
import kotlin.math.sign

fun main() {
    fun adjacent(head: Pair<Int, Int>, tail: Pair<Int, Int>) =
        (tail.first - head.first).absoluteValue <= 1 && (tail.second - head.second).absoluteValue <= 1

    fun solve(points: Int, instructions: List<String>): Int {
        val pointList = MutableList(points) { Pair(0, 0) }
        val visited = hashSetOf<Pair<Int, Int>>()

        instructions.forEach {
            val (direction, i) = it.split(' ')

            repeat(i.toInt()) {
                pointList[0] = when (direction) {
                    "U" -> pointList[0].copy(second = pointList[0].second + 1)
                    "D" -> pointList[0].copy(second = pointList[0].second - 1)
                    "R" -> pointList[0].copy(first = pointList[0].first + 1)
                    else -> pointList[0].copy(first = pointList[0].first - 1)
                }

                for (point in 1 until pointList.size) {
                    val currentHead = pointList[point - 1]
                    val tail = pointList[point]

                    if (!adjacent(currentHead, tail)) {
                        val xSign = (currentHead.first - tail.first).sign
                        val ySign = (currentHead.second - tail.second).sign
                        pointList[point] = tail.copy(
                            first = tail.first + xSign,
                            second = tail.second + ySign
                        )
                    }
                }

                visited.add(pointList.last())

                // Debug:
//                println("HEAD: ${pointList[0].first} ${pointList[1].second} - TAIL: ${pointList[pointList.size - 1].first} ${pointList[pointList.size - 1].second}")
            }
        }

        println(visited.size)
        return visited.size
    }

    fun part1(input: List<String>): Int {
        return solve(points = 2, input)
    }

    fun part2(input: List<String>): Int {
        return solve(points = 10, input)
    }

    val testInput = readInput("Day09_test")
    val testInput2 = readInput("Day09_test2")
    check(part1(testInput) == 13)
    check(part2(testInput) == 1)
    check(part2(testInput2) == 36)

    val input = readInput("Day09")
    println(part1(input))
    println(part2(input))
}

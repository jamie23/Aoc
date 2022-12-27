import Direction.*
import kotlin.math.max

fun main() {
    fun part1(input: List<String>): Int {
        val height = input.size
        val width = input[0].length

        val set: HashSet<Pair<Int, Int>> = hashSetOf()

        for (i in 0 until height) {
            var currMaxX = -1
            var currMaxY = -1
            var currMaxXReverse = -1
            var currMaxYReverse = -1

            for (j in 0 until width) {
                val currValX = input[i][j].digitToInt()
                val currValY = input[j][i].digitToInt()
                val currValXReverse = input[height - 1 - i][width - 1 - j].digitToInt()
                val currValYReverse = input[width - 1 - j][height - 1 - i].digitToInt()

                if (currValX > currMaxX) {
                    set.add(Pair(i, j))
                    currMaxX = currValX
                }

                if (currValY > currMaxY) {
                    set.add(Pair(j, i))
                    currMaxY = currValY
                }

                if (currValXReverse > currMaxXReverse) {
                    set.add(Pair(width - i - 1, height - j - 1))
                    currMaxXReverse = currValXReverse
                }

                if (currValYReverse > currMaxYReverse) {
                    set.add(Pair(height - j - 1, width - i - 1))
                    currMaxYReverse = currValYReverse
                }
            }
        }

        return set.size
    }

    fun List<String>.parseToDigitList() = map { row ->
        row.map { char -> char.digitToInt() }
    }

    fun pathToFirstBlocker(list: List<Int>, currentHeight: Int): Int {
        val firstBlocker = list.indexOfFirst { it >= currentHeight } + 1
        if (firstBlocker == 0) return list.size
        return firstBlocker
    }

    fun List<List<Int>>.colSubList(col: Int, start: Int, direction: Direction): List<Int> {
        val list = mutableListOf<Int>()
        val range = if (direction == DOWN) {
            start until get(col).size
        } else {
            start downTo 0
        }

        for (i in range) list.add(get(i)[col])
        return list
    }

    fun List<Int>.scenicScore(): Int {
        val start = get(0)
        val score = this.drop(1).takeWhile { it < start }.size

        if (score + 1 == size) return score
        return score + 1
    }

    fun List<List<Int>>.maxScenicScore(): Int {
        // For each element in middle of list, get left, right, up, down  scenic scores and multiply them
        // return max

        val range = 1 until size - 1
        var max = -1

        for (i in range) {
            for (j in range) {
                val right = get(i).subList(j, size).scenicScore()
                val left = get(i).subList(0, j + 1).reversed().scenicScore()
                val top = colSubList(j, i, UP).scenicScore()
                val down = colSubList(j, i, DOWN).scenicScore()

                val total = right * left * top * down
                max = max(total, max)
            }
        }
        return max
    }

    fun checkUtils() {
        // Part 2 tests
        check(pathToFirstBlocker(listOf(4, 2, 3), 3) == 1)
        check(pathToFirstBlocker(listOf(3, 9, 9), 4) == 2)
        check(pathToFirstBlocker(listOf(5, 2, 3), 6) == 3)

        val listTest = listOf(
            "123",
            "456",
            "789"
        )

        check(
            listTest
                .parseToDigitList()
                .colSubList(0, 0, DOWN) == listOf(1, 4, 7)
        )

        check(
            listTest
                .parseToDigitList()
                .colSubList(1, 1, DOWN) == listOf(5, 8)
        )

        check(
            listTest
                .parseToDigitList()
                .colSubList(2, 2, UP) == listOf(9, 6, 3)
        )

        check(
            listTest
                .parseToDigitList()
                .colSubList(1, 1, UP) == listOf(5, 2)
        )

        check(
            listOf(5, 2, 9).scenicScore() == 2
        )

        check(listOf(2, 5, 9).scenicScore() == 1)
    }

    fun part2(input: List<String>) = input
        .parseToDigitList()
        .maxScenicScore()

    val testInput = readInput("Day08_test")
    check(part1(testInput) == 21)
    check(part2(testInput) == 8)
    checkUtils()

    val input = readInput("Day08")
    println(part1(input))
    println(part2(input))
}

private enum class Direction {
    UP, DOWN
}
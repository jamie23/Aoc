import kotlin.IllegalArgumentException
import kotlin.math.min

fun main() {
    var current = 0
    var p1Results = 0
    var dirToDelete = Integer.MAX_VALUE

    fun generateFileSystem(node: Node, commands: List<Command>) {
        while (current < commands.size) {
            val command = commands[current]
            current++
            when (command) {
                is Cd -> {
                    if (command.dir == "..") return
                    generateFileSystem(
                        node.files?.first { it.name == command.dir } ?: throw IllegalArgumentException(),
                        commands
                    )
                }

                is Dir -> node.files = command.files
            }
        }
    }

    fun dfs(root: Node) {
        root.files?.filter { it.isDir }?.forEach {
            dfs(it)
        }

        val total = root.files?.sumOf { it.size ?: 0 } ?: 0
        root.size = total
        if (total <= 100000) {
            p1Results += total
        } else if (total >= 10216456) {
            dirToDelete = min(dirToDelete, total)
        }
    }

    fun solution(input: String): Int {
        val root = Node(
            name = "/",
            isDir = true
        )

        val commands = input
            .split("$")
            .drop(1)
            .mapToCommand()

        generateFileSystem(root, commands)
        dfs(root)

        println("Part 2: $dirToDelete")
        return p1Results
    }

    val testInput = readInputText("Day07_test")
    check(solution(testInput) == 95437)

    val input = readInputText("Day07")
    println(solution(input))
}

private fun List<String>.mapToCommand() = map {
    val command = it.substring(1, 3)
    val output = it.substring(4)

    when (command) {
        "ls" -> Dir(output.split('\n').dropLast(1).toNode())
        else -> Cd(output.split('\n').first())
    }
}

private fun List<String>.toNode() = map {
    val (a, b) = it.split(" ")
    when (a) {
        "dir" -> Node(name = b, isDir = true)
        else -> Node(name = b, size = a.toInt(), isDir = false)
    }
}.toHashSet()

data class Node(
    val name: String,
    var files: HashSet<Node>? = null,
    var size: Int? = null,
    val isDir: Boolean
)

sealed class Command()
class Dir(val files: HashSet<Node>) : Command()
class Cd(val dir: String) : Command()

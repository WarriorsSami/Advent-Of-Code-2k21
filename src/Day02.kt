
fun main() {
    fun part1(input: List<String>): Int {
        var result: Int
        input.map {
            val tuple = it.split(" ")
            Pair(tuple[0], tuple[1].toInt())
        }.fold(Pair(0, 0)) { acc, (command, value) ->
            when (command) {
                "forward" -> Pair(acc.first + value, acc.second)
                "up" -> Pair(acc.first, acc.second - value)
                "down" -> Pair(acc.first, acc.second + value)
                else -> acc
            }
        }.run {
            result = first * second
        }
        return result
    }

    fun part2(input: List<String>): Int {
        var result: Int
        input.map {
            val tuple = it.split(" ")
            Pair(tuple[0], tuple[1].toInt())
        }.fold(Triple(0, 0, 0)) { acc, (command, value) ->
            when (command) {
                "forward" -> Triple(acc.first + value,
                    acc.second + acc.third * value, acc.third)
                "up" -> Triple(acc.first, acc.second, acc.third - value)
                "down" -> Triple(acc.first, acc.second, acc.third + value)
                else -> acc
            }
        }.run {
            result = first * second
        }
        return result
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 150)

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}

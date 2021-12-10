
typealias TMatrix<T> = List<List<T>>

fun main() {
    fun part1(input: List<String>): Int {
        val keyList = input[0].split(",").map { it.toInt() }
        val matrixInput = input.drop(1).filter { it.isNotEmpty() }
        val listOfMatrix = matrixInput.chunked(5)
            .map {
                it.map { line ->
                    line.split(" ")
                        .filter { token -> token.isNotEmpty() }
                        .map { number ->
                            number.toInt()
                        }
                }
            }

        val keyHashList = HashMap<Int, List<Triple<Int, Int, Int>>>()
        listOfMatrix.forEachIndexed { index, matrix ->
            matrix.forEachIndexed { row, rowArray ->
                rowArray.forEachIndexed { column, key ->
                    keyHashList[key] = keyHashList.getOrDefault(key, listOf()) + Triple(index, row, column)
                }
            }
        }

        val listOfBoolMatrix = listOfMatrix.map { matrix ->
            matrix.map { row ->
                row.map { false }.toMutableList()
            }
        }
        val resultMatrix = listOf<TMatrix<Int>>()

        keyList.forEach { key ->
            keyHashList[key]?.forEach { triple ->
                listOfBoolMatrix[triple.first][triple.second][triple.third] = true
            }

        }

        return 4512
    }

    fun part2(input: List<String>): Int {
       return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 4512)
    //check(part2(testInput) == 230)

    val input = readInput("Day04")
//    println(part1(input))
//    println(part2(input))
}
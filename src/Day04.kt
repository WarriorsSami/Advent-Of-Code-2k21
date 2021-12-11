import java.util.List.copyOf

typealias MutMatrix<T> = List<MutableList<T>>

fun main() {
    fun<T> MutMatrix<T>.transpose(): MutMatrix<T> {
        val defaultValue = this[0][0]
        val result = this[0].map { MutableList(this.size) { defaultValue } }
        forEachIndexed { i, row ->
            row.forEachIndexed { j, value ->
                result[j][i] = value
            }
        }
        return result
    }

    fun MutMatrix<Boolean>.isBingoValid(): Boolean {
        val transposedThis = this.transpose()
        return this.any { row -> row.all { it } } ||
               transposedThis.any { row -> row.all { it } }
    }

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
        var resultListOfMatrix: List<MutMatrix<Boolean>>
        var resultIndex = 0
        var resultKey = 0
        var resultBoolMatrix = listOfBoolMatrix[0]
            .map { row -> row.map { false }.toMutableList() }

        run loop@ {
            keyList.forEach { key ->
                keyHashList[key]?.forEach { triple ->
                    listOfBoolMatrix[triple.first][triple.second][triple.third] = true
                }
                resultListOfMatrix = listOfBoolMatrix.filter { it.isBingoValid() }
                if (resultListOfMatrix.isNotEmpty()) {
                    resultBoolMatrix = resultListOfMatrix[0]
                    resultIndex = listOfBoolMatrix.indexOf(resultBoolMatrix)
                    resultKey = key
                    return@loop
                }
            }
        }

        return resultKey * listOfMatrix[resultIndex]
            .mapIndexed { row, rowArray ->
                rowArray.filterIndexed { index, _ ->
                    !resultBoolMatrix[row][index]
                }
            }.flatten()
            .sum()
    }

    fun part2(input: List<String>): Int {
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
        var resultListOfMatrix: List<MutMatrix<Boolean>>
        val bingoValidList = ArrayList<Int>()

        var resultIndex = 0
        var resultKey = 0
        var resultBoolMatrix = listOfBoolMatrix[0]
            .map { row ->
                row.map { false }.toMutableList()
            }

        keyList.forEach { key ->
            keyHashList[key]?.forEach { triple ->
                listOfBoolMatrix[triple.first][triple.second][triple.third] = true
            }

            resultListOfMatrix = listOfBoolMatrix
                .filterIndexed { index, it ->
                    it.isBingoValid() && !bingoValidList.contains(index)
                }
            resultListOfMatrix.forEach {
                bingoValidList.add(listOfBoolMatrix.indexOf(it))
            }

            if (resultListOfMatrix.isNotEmpty()) {
                resultBoolMatrix = resultListOfMatrix[0].map { copyOf(it) }
                resultIndex = listOfBoolMatrix.indexOf(resultBoolMatrix)
                resultKey = key
            }
        }

        val sum = listOfMatrix[resultIndex]
            .mapIndexed { row, rowArray ->
                rowArray.filterIndexed { index, _ ->
                    !resultBoolMatrix[row][index]
                }
            }.flatten()
            .sum()

        return resultKey * sum
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 4512)
    check(part2(testInput) == 1924)

    val input = readInput("Day04")
    println(part1(input))
    println(part2(input))
}
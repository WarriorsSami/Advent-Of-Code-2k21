
fun main() {
    var score = 0
    val numberOfSteps = 100
    val flashRate = 9

    fun part1(input: List<String>): Int {
        var matrixOfInteger = input.map {
            it.map { elem ->
                elem.toString().toInt()
            }.toMutableList()
        }

        for (index in 0 until numberOfSteps) {
            val flashMatrix = input.map {
                it.map { false }
                    .toMutableList()
            }

            // increment every element in the matrix
            matrixOfInteger = matrixOfInteger.map {
                it.map { elem ->
                    elem + 1
                }.toMutableList()
            }

            // collect the flashing elements into a queue
            val queueOfFlashLight = matrixOfInteger.asSequence().mapIndexed { row, rowArray ->
                rowArray.mapIndexed { col, elem ->
                    Triple(row, col, elem)
                }
            }.flatten()
                .filter { it.third > flashRate }
                .map { Pair(it.first, it.second) }
                .toMutableList()

            // mark them as flashing
            queueOfFlashLight.forEach {
                flashMatrix[it.first][it.second] = true
            }

            // while there is no more flashing, increment the score
            while (queueOfFlashLight.isNotEmpty()) {
                val (row, col) = queueOfFlashLight.removeAt(0)
                score++
                matrixOfInteger[row][col] = 0

                // iterate over all neighbors
                for (i in -1..1) {
                    for (j in -1..1) {
                        if (i == 0 && j == 0) continue
                        val neighborRow = row + i
                        val neighborCol = col + j

                        if (neighborRow in matrixOfInteger.indices &&
                            neighborCol in 0 until matrixOfInteger[0].size) {
                            if (!flashMatrix[neighborRow][neighborCol]) {
                                matrixOfInteger[neighborRow][neighborCol]++

                                if (matrixOfInteger[neighborRow][neighborCol] > flashRate) {
                                    flashMatrix[neighborRow][neighborCol] = true
                                    queueOfFlashLight.add(Pair(neighborRow, neighborCol))
                                }
                            }
                        }
                    }
                }
            }
        }
        return score
    }

    fun part2(input: List<String>): Int {
        var momentWhenAllAreFlashing = 0
        var index = 0

        var matrixOfInteger = input.map {
            it.map { elem ->
                elem.toString().toInt()
            }.toMutableList()
        }

        // get matrix rows * columns
        val rows = matrixOfInteger.size
        val cols = matrixOfInteger[0].size
        val total = rows * cols

        while (true) {
            ++index
            var score = 0

            val flashMatrix = input.map {
                it.map { false }
                    .toMutableList()
            }

            // increment every element in the matrix
            matrixOfInteger = matrixOfInteger.map {
                it.map { elem ->
                    elem + 1
                }.toMutableList()
            }

            // collect the flashing elements into a queue
            val queueOfFlashLight = matrixOfInteger.asSequence().mapIndexed { row, rowArray ->
                rowArray.mapIndexed { col, elem ->
                    Triple(row, col, elem)
                }
            }.flatten()
                .filter { it.third > flashRate }
                .map { Pair(it.first, it.second) }
                .toMutableList()

            // mark them as flashing
            queueOfFlashLight.forEach {
                flashMatrix[it.first][it.second] = true
            }

            // while there is no more flashing, increment the score
            while (queueOfFlashLight.isNotEmpty()) {
                val (row, col) = queueOfFlashLight.removeAt(0)
                score++
                matrixOfInteger[row][col] = 0

                // iterate over all neighbors
                for (i in -1..1) {
                    for (j in -1..1) {
                        if (i == 0 && j == 0) continue
                        val neighborRow = row + i
                        val neighborCol = col + j

                        if (neighborRow in matrixOfInteger.indices &&
                            neighborCol in 0 until matrixOfInteger[0].size) {
                            if (!flashMatrix[neighborRow][neighborCol]) {
                                matrixOfInteger[neighborRow][neighborCol]++

                                if (matrixOfInteger[neighborRow][neighborCol] > flashRate) {
                                    flashMatrix[neighborRow][neighborCol] = true
                                    queueOfFlashLight.add(Pair(neighborRow, neighborCol))
                                }
                            }
                        }
                    }
                }
            }

            if (score == total) {
                momentWhenAllAreFlashing = index
                break
            }
        }
        return momentWhenAllAreFlashing
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day11_test")
    check(part1(testInput) == 1656)
    check(part2(testInput) == 195)

    val input = readInput("Day11")
    println(part1(input))
    println(part2(input))
}

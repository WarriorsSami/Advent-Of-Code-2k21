import kotlin.math.pow

fun main() {
    fun List<Char>.toDecimal(): Int {
        return map { if (it == '1') 1 else 0 }
            .reversed()
            .fold(Pair(0, 0)) { acc, i ->
                Pair(acc.first + i * 2.0.pow(acc.second).toInt(), acc.second + 1)
            }.first
    }

    fun part1(input: List<String>): Int {
        val transposedArray = Array(input[0].length) { CharArray(input.size) }
        input.map { it.toCharArray() }
           .forEachIndexed { index, charArray ->
               charArray.forEachIndexed { innerIndex, c ->
                   transposedArray[innerIndex][index] = c
               }
           }

        val gammaString = transposedArray.map { outerIt ->
            outerIt.groupBy { it }
                .maxByOrNull { innerIt -> innerIt.value.size }!!.key
        }
        val epsilonString = gammaString.map {
            if (it == '1') '0' else '1'
        }

        return gammaString.toDecimal() * epsilonString.toDecimal()
    }

    fun List<CharArray>.filterStorage(criterion: Boolean): Int {
        var index = 0
        val indexUpperBound = map { it.size }.minOf { it }
        var initialList = this

        while (initialList.size > 1 && index < indexUpperBound) {
            val listOfOnes = initialList.filter { it[index] == '1' }
            val listOfZeros = initialList.filter { it[index] == '0' }

            initialList = when (criterion) {
                true -> {
                    var mostFreqBit = initialList.map { it[index] }
                        .groupBy { it }
                        .maxByOrNull { it.value.size }!!.key

                    if (listOfOnes.size == listOfZeros.size) {
                        mostFreqBit = '1'
                    }
                    initialList.filter { it[index] == mostFreqBit }
                }
                false -> {
                    var leastFreqBit = initialList.map { it[index] }
                        .groupBy { it }
                        .minByOrNull { it.value.size }!!.key

                    if (listOfZeros.size == listOfOnes.size) {
                        leastFreqBit = '0'
                    }
                    initialList.filter { it[index] == leastFreqBit }
                }
            }

            ++index
        }

        return initialList[0].toList().toDecimal()
    }

    fun part2(input: List<String>): Int {
        val oxygenStorage = input.map { it.toCharArray() }.filterStorage(true)
        val co2Storage = input.map { it.toCharArray() }.filterStorage(false)

        return oxygenStorage * co2Storage
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 198)
    check(part2(testInput) == 230)

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}
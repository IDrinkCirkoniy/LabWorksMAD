import kotlin.random.Random

fun main() {
    val n = 8
    var attempts = 0
    var solution: List<Int>? = null

    while (solution == null) {
        attempts++

        val candidate = generateRandomPlacement(n)

        if (attempts % 1000 == 0) {
            println("Попытка #$attempts:")
            printBoard(candidate)
            println("Проверка: ${if (isSafe(candidate)) "Верное решение" else "Ферзи бьют друг друга"}\n")
        }

        if (isSafe(candidate)) {
            solution = candidate
        }
    }

    println("\nРЕШЕНИЕ НАЙДЕНО за $attempts попыток!")
    println("Доска:")
    printBoard(solution)
}

fun generateRandomPlacement(n: Int): List<Int> {
    return List(n) { Random.nextInt(n) }
}

fun isSafe(placement: List<Int>): Boolean {
    val n = placement.size
    val cols = BooleanArray(n)
    val diag1 = BooleanArray(2 * n - 1)
    val diag2 = BooleanArray(2 * n - 1)

    for (row in 0 until n) {
        val col = placement[row]
        val d1 = row - col + (n - 1)
        val d2 = row + col

        if (cols[col] || diag1[d1] || diag2[d2]) {
            return false
        }

        cols[col] = true
        diag1[d1] = true
        diag2[d2] = true
    }
    return true
}

fun printBoard(placement: List<Int>) {
    for (row in placement.indices) {
        for (col in placement.indices) {
            if (placement[row] == col) print(" Q ") else print(" x ")
        }
        println()
    }
}
import kotlin.math.PI
import kotlin.math.pow

fun main() {
//    Task1

    fun geometricProgressionTerm(n: Int, firstTerm: Int = 1, ratio: Int = 2): Double {
        require(n >= 1) { "n должно быть >= 1" }
        return firstTerm.toDouble() * ratio.toDouble().pow(n - 1)
    }

    println("Геометрическая прогрессия:")
    println("  n = 5 (по умолчанию первый элемент = 1, шаг = 2): ${geometricProgressionTerm(5)}")
    println("  n = 4, первый элемент = 3, шаг = 2: ${geometricProgressionTerm(4, 3, 2)}")
    println("  n = 1, первый элемент = 100, шаг = 5: ${geometricProgressionTerm(1, 100, 5)}")
    println("  n = 3, первый элемент = 2, шаг = 3: ${geometricProgressionTerm(3, 2, 3)}")

//    Task2

    fun area(radius: Double): Double {
        return PI * radius * radius
    }

    fun area(majorAxis: Double, minorAxis: Double): Double {
        return PI * majorAxis * minorAxis
    }

    println()
    println("Площади:")
    println("  Круг радиусом 3: ${area(3.0)}")
    println("  Круг радиусом 5: ${area(5.0)}")
    println("  Эллипс с полуосями 4 и 2: ${area(4.0, 2.0)}")
    println("  Эллипс с полуосями 6 и 3: ${area(6.0, 3.0)}")

//    Task3

    fun range(vararg numbers: Double): Double {
        if (numbers.isEmpty()) return 0.0
        return numbers.maxOrNull()!! - numbers.minOrNull()!!
    }

    println()
    println("Размах ряда:")
    println("  Числа: 14, 27, 34, 47, 54 -> размах = ${range(14.0, 27.0, 34.0, 47.0, 54.0)}")
    println("  Числа: 5.2, 2.8, 8.1, 3.7 -> размах = ${range(5.2, 2.8, 8.1, 3.7)}")
    println("  Числа: 128, 66 -> размах = ${range(128.0, 66.0)}")
    println("  Одно число: 42 -> размах = ${range(42.0)}")
    println("  Пустой список: ${range()}")

//    Task4

    fun createInterestCalculator(type: String): (Double, Double, Int) -> Double {
        return when (type.lowercase()) {
            "simple" -> { s0: Double, r: Double, n: Int ->
                s0 * (1 + (r / 100.0) * n)
            }
            "compound" -> { s0: Double, r: Double, n: Int ->
                s0 * (1 + r / 100.0).pow(n)
            }
            else -> throw IllegalArgumentException("Тип процентов должен быть 'simple' или 'compound'")
        }
    }

    println()
    println("Расчёт процентов:")
    println("  1000 руб, 5%, 2 года (простые): ${createInterestCalculator("simple")(1000.0, 5.0, 2)}")
    println("  1000 руб, 5%, 2 года (сложные): ${createInterestCalculator("compound")(1000.0, 5.0, 2)}")

//    Task5

    fun cartItem(productName: String, price: Double): (Int) -> String {
        return { quantity: Int ->
            val total = price * quantity
            "  В корзину добавлен $productName на сумму $total руб."
        }
    }

    val cat = cartItem("Кот", 5000.0)
    val console = cartItem("Консоль", 41000.0)
    val book = cartItem("Книга", 800.0)

    println()
    println("Функция-замыкание:")
    println(cat(3))         // 3 кота
    println(console(1))     // 1 консоль
    println(book(2))        // 2 книги
    println(cat(10))        // 10 кошек
    println(cartItem("Кофий", 450.0)(2))  // создали и добавили продукт

    println()
    println("The End!")
}
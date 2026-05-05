fun main() {

    val book1 = Book("Война и мир", "Лев Толстой", 1869, 1300)
    book1.printInfo()

    book1.year = 1867
    book1.pages = 1337
    println("\nПосле изменения свойств:")
    book1.printInfo()

    val book2 = Book("Преступление и наказание", "Фёдор Достоевский", 1866, 672)
    println()
    println("Создали объект класса через первичный конструктор:")
    book2.printInfo()

    // Вторичный конструктор (только название)
    val book3 = Book("Мастер и Маргарита")
    book3.author = "Михаил Булгаков"
    book3.year = 1967
    book3.pages = 480
    println()
    println("Создали объект класса через вторичный конструктор:")
    book3.printInfo()

    val book4 = Book("1984", "Джордж Оруэлл", 1949, 328)
    book4.printInfo()
    println()
    println(book4.pagesInfo)

    val book5 = Book("Евгений Онегин", "Александр Пушкин", 1834, 321)
    println(book5.printInfo())
    println()

    println("Исправим данные")
    try {
        book5.title = "Капитанская дочка"
        book5.author = "Александр Пушкин"
        book5.year = 1836
        book5.pages = 320
        println("Успешно изменено: \"${book5.title}\", ${book5.author}, ${book5.year} г., ${book5.pages} стр.")
    } catch (e: IllegalArgumentException) {
        println("Ошибка: ${e.message}")
    }

    println("\nВведены некорректные данные")

//  Исключения

    try {
        book5.title = ""
    } catch (e: IllegalArgumentException) {
        println("Ошибка при вводе названия: ${e.message}")
    }

    try {
        book5.author = ""
    } catch (e: IllegalArgumentException) {
        println("Ошибка при вводе автора: ${e.message}")
    }

    try {
        book5.year = 1400
    } catch (e: IllegalArgumentException) {
        println("Ошибка при вводе года: ${e.message}")
    }

    try {
        book5.pages = 0
    } catch (e: IllegalArgumentException) {
        println("Ошибка при вводе страниц (ноль): ${e.message}")
    }

    try {
        book5.pages = -50
    } catch (e: IllegalArgumentException) {
        println("Ошибка при вводе страниц (отрицательное): ${e.message}")
    }

    println()
    val books = arrayOf(
        Book("Война и мир", "Лев Толстой", 1869, 1300),
        Book("Преступление и наказание", "Фёдор Достоевский", 1866, 672),
        Book("Мастер и Маргарита", "Михаил Булгаков", 1967, 480)
    )

    println("Список всех книг в массиве:")
    for ((index, book) in books.withIndex()) {
        println("${index + 1}. \"${book.title}\" - ${book.author} (${book.year} г.) - ${book.pages} стр.")
    }

    print("Введите кол-во страниц: ")
    val pagesThreshold = readln().toInt()
    println("\nПоиск книг с количеством страниц > $pagesThreshold:")
    val thickBooks = books.filter { it.pages > pagesThreshold }
    if (thickBooks.isEmpty()) {
        println("Книг с количеством страниц > $pagesThreshold не найдено")
    } else {
        thickBooks.forEach {
            println("  \"${it.title}\" - ${it.pages} стр.")
        }
    }

    print("Введите год: ")
    val yearThreshold = readln().toInt()
    println("\nПоиск книг, изданных после $yearThreshold года:")
    val modernBooks = books.filter { it.year > yearThreshold }
    if (modernBooks.isEmpty()) {
        println("Книг, изданных после $yearThreshold года, не найдено")
    } else {
        modernBooks.forEach {
            println("  \"${it.title}\" - ${it.year} г.")
        }
    }

    println("\nПоиск книг, автор которых начинается на 'Ф':")
    val fAuthorBooks = books.filter { it.author.startsWith("Ф", ignoreCase = true) }
    if (fAuthorBooks.isEmpty()) {
        println("Книг с автором на 'Ф' не найдено")
    } else {
        fAuthorBooks.forEach {
            println("  \"${it.title}\" - ${it.author}")
        }
    }

    println("The End!")
}
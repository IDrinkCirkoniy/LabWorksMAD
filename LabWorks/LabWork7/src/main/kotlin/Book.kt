class Book(
    title: String,
    author: String,
    year: Int,
    pages: Int
) {
    var title: String = title
        set(value) {
            require(value.isNotBlank()) { "Название не может быть пустым" }
            field = value
        }
        get() = field

    var author: String = author
        set(value) {
            require(value.isNotBlank()) { "Автор не может быть пустым" }
            field = value
        }
        get() = field

    var year: Int = year
        set(value) {
            require(value in 1440..2026) { "Год издания должен быть от 1440 до 2026" }
            field = value
        }
        get() = field

    var pages: Int = pages
        set(value) {
            require(value > 0) { "Количество страниц должно быть больше 0" }
            field = value
        }
        get() = field

    init {
        require(title.isNotBlank()) { "Название не может быть пустым" }
        require(author.isNotBlank()) { "Автор не может быть пустым" }
        require(year in 1440..2026) { "Год издания должен быть от 1440 до 2026" }
        require(pages > 0) { "Количество страниц должно быть больше 0" }
    }

    constructor(title: String) : this(title, "Неизвестный автор", 2000, 100)

    fun printInfo() {
        print("Книга: ")
        println("Название: $title")
        println("Автор: $author")
        println("Год издания: $year")
        println("Количество страниц: $pages")
    }

    val pagesInfo: String
        get() = "Количество страниц: $pages"
}
data class Action(
    val name: Name,
    val type: Type
) {
    enum class Name { FULLSCREEN, UP, DOWN, LEFT, RIGHT }
    enum class Type { START, END }
}

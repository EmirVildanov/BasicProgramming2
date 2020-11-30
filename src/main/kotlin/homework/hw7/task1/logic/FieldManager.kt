package homework.hw7.task1.logic

class FieldManager(
    private val idValuesField: List<Int>,
    private val linearSize: Int
) {
    private val cells = idValuesField.withIndex().map { Cell(it.index, it.value) }

    data class Cell(val index: Int, val playerId: Int)

    data class Line(val data: List<Cell>) {
        private val playersInLine = data.map { it.playerId }.filter { it != -1 }.distinct()
        private val freeCellsNumber = data.count { it.playerId == -1 }
        val isAlmostCaptured = (playersInLine.size == 1) && freeCellsNumber == 1
        val isLineCaptured = (playersInLine.size == 1) && freeCellsNumber == 0
        val capturingPlayer = if (isAlmostCaptured || isLineCaptured) playersInLine[0] else -1
        val firstFreeCell = data.find { it.playerId == -1 }
    }

    fun getWinner(): Int? {
        return getAllLines().find { it.isLineCaptured }?.capturingPlayer
    }

    fun getAlmostCapturedLineBy(playerId: Int): Line? {
        return getAllLines().find { it.isAlmostCaptured && it.capturingPlayer == playerId }
    }

    private fun getAllLines(): List<Line> {
        return listOf(getRows(), getColumns(), getDiagonals()).flatten()
    }

    private fun getRows(): List<Line> {
        return cells.chunked(linearSize).map { Line(it) }
    }

    private fun getColumns(): List<Line> {
        return IntRange(0, linearSize - 1).map { i -> Line(cells.slice(i until idValuesField.size step linearSize)) }
    }

    private fun getDiagonals(): List<Line> {
        val rows = getRows()
        val mainDiagonal = Line((0 until linearSize).map { i -> rows[i].data[i] })
        val secondaryDiagonal = Line((0 until linearSize).map { i -> rows[i].data[linearSize - i - 1] })
        return listOf(mainDiagonal, secondaryDiagonal)
    }
}

package homework.hw7.task1

import homework.hw7.task1.enums.BotDifficulty
import homework.hw7.task1.enums.CellType
import homework.hw7.task1.enums.GameOverCheck
import kotlin.math.pow
import kotlin.math.sqrt

object Bot {
    fun makeBotTurn() {
        when (botDifficulty) {
            BotDifficulty.EASY -> {
                easyTurn()
            }
            BotDifficulty.MEDIUM -> {
                mediumTurn()
            }
            BotDifficulty.HARD -> {
                hardTurn()
            }
        }
    }

    private fun randomTurn(sideTurn: Boolean = false, cornerTurn: Boolean = false) {
        val randomCellsArray = cellsArray.shuffled()
        val isSpecialTurn = sideTurn || cornerTurn
        for (i in randomCellsArray.indices) {
            if (randomCellsArray[i].activeStatus) {
                if (!isSpecialTurn || isSideTurnAvailable(sideTurn, i) || isCornerTurnAvailable(cornerTurn, i)) {
                    putFigure(randomCellsArray[i])
                    break
                }
            }
        }
    }

    private fun isSideTurnAvailable(sideTurn: Boolean, index: Int) =
        sideTurn && NOT_CORNER_INDEXES.contains(index)

    private fun isCornerTurnAvailable(cornerTurn: Boolean, index: Int) =
        cornerTurn && CORNER_INDEXES.contains(index)

    private fun distributeCells(permitted: MutableList<Int>, player: MutableList<Int>, bot: MutableList<Int>) {
        permitted.forEach {
            if (crossTurn) {
                field[it] = CellType.CIRCLE
                if (PlayingField.checkGameOver() == GameOverCheck.CIRCLE_WON) {
                    player.add(it)
                }
                field[it] = CellType.NOTHING

                field[it] = CellType.CROSS
                if (PlayingField.checkGameOver() == GameOverCheck.CROSS_WON) {
                    bot.add(it)
                }
                field[it] = CellType.NOTHING
            } else {
                field[it] = CellType.CROSS
                if (PlayingField.checkGameOver() == GameOverCheck.CROSS_WON) {
                    player.add(it)
                }
                field[it] = CellType.NOTHING

                field[it] = CellType.CIRCLE
                if (PlayingField.checkGameOver() == GameOverCheck.CIRCLE_WON) {
                    bot.add(it)
                }
                field[it] = CellType.NOTHING
            }
        }
    }

    private fun easyTurn() {
        val permittedCellsArray = cellsArray.filter { it.activeStatus }.map { it.cellNumber }.toMutableList()
        val playerWonCellsArray = mutableListOf<Int>()
        val botWonCellsArray = mutableListOf<Int>()
        distributeCells(permittedCellsArray, playerWonCellsArray, botWonCellsArray)
        permittedCellsArray.removeAll(playerWonCellsArray)
        permittedCellsArray.removeAll(botWonCellsArray)
        if (permittedCellsArray.isNotEmpty()) {
            putFigure(cellsArray[permittedCellsArray.shuffled()[0]])
        } else {
            randomTurn()
        }
    }

    private fun mediumTurn() {
        val permittedCellsArray = mutableListOf<Int>()
        cellsArray.forEach { if (it.activeStatus) permittedCellsArray.add(it.cellNumber) }
        val playerWonCellsArray = mutableListOf<Int>()
        val botWonCellsArray = mutableListOf<Int>()
        distributeCells(permittedCellsArray, playerWonCellsArray, botWonCellsArray)
        if (botWonCellsArray.isNotEmpty()) {
            putFigure(cellsArray[botWonCellsArray[0]])
        } else if (playerWonCellsArray.isNotEmpty()) {
            putFigure(cellsArray[playerWonCellsArray[0]])
        } else {
            randomTurn()
        }
    }

    private fun putFigureOnTheMostRemoteCell(): Boolean {
        val lastPlayerX = lastPlayerTurn / FIELD_SIDE
        val lastPlayerY = lastPlayerTurn % FIELD_SIDE
        val appropriateCellsArray = mutableListOf<Cell>()
        var maxDistance = 0.0
        for (x in 0 until FIELD_SIDE) {
            for (y in 0 until FIELD_SIDE) {
                val distance = sqrt((x - lastPlayerX).toDouble().pow(2) + (y - lastPlayerY).toDouble().pow(2))
                if (distance == maxDistance) {
                    appropriateCellsArray.add(cellsArray[(x * FIELD_SIDE + y)])
                } else if (distance > maxDistance) {
                    maxDistance = distance
                    appropriateCellsArray.clear()
                    appropriateCellsArray.add(cellsArray[(x * FIELD_SIDE + y)])
                }
            }
        }
        appropriateCellsArray.forEach {
            if (putFigure(it)) {
                return true
            }
        }
        return false
    }

    private fun checkOppositeSecondPlayerTurn(): Boolean {
        val firstPlayerTurnRow = firstPlayerTurnCellIndex / FIELD_SIDE
        val firstPlayerTurnColumn = firstPlayerTurnCellIndex % FIELD_SIDE
        val secondPlayerTurnRow = secondPlayerTurnCellIndex / FIELD_SIDE
        val secondPlayerTurnColumn = secondPlayerTurnCellIndex % FIELD_SIDE
        if (firstPlayerTurnRow == FIELD_SIDE - secondPlayerTurnRow &&
            firstPlayerTurnColumn == FIELD_SIDE - secondPlayerTurnColumn) {
            return true
        }
        return false
    }

    private fun hardTurn() {
        val permittedCellsArray = mutableListOf<Int>()
        cellsArray.forEach { if (it.activeStatus) permittedCellsArray.add(it.cellNumber) }
        val playerWonCellsArray = mutableListOf<Int>()
        val botWonCellsArray = mutableListOf<Int>()
        distributeCells(permittedCellsArray, playerWonCellsArray, botWonCellsArray)
        if (botWonCellsArray.isNotEmpty()) {
            putFigure(cellsArray[botWonCellsArray[0]])
        } else if (playerWonCellsArray.isNotEmpty()) {
            putFigure(cellsArray[playerWonCellsArray[0]])
        } else {
            if (crossTurn) {
                if (field[4] == CellType.NOTHING) {
                    putFigure(cellsArray[4])
                } else if (!putFigureOnTheMostRemoteCell()) {
                    randomTurn()
                }
            } else {
                if (field[4] == CellType.NOTHING) {
                    putFigure(cellsArray[4])
                } else if (field[4] == CellType.CIRCLE) {
                    println(firstPlayerTurnCellIndex)
                    println(secondPlayerTurnCellIndex)
                    if (firstPlayerTurnCellIndex == 0 || firstPlayerTurnCellIndex == 2 || firstPlayerTurnCellIndex == 6 || firstPlayerTurnCellIndex == 8) {
                        if (firstPlayerTurnCellIndex == 0) {
                            if (!putFigure(cellsArray[8])) {
                                randomTurn(true)
                            }
                        } else if (firstPlayerTurnCellIndex == 2) {
                            if (!putFigure(cellsArray[6])) {
                                randomTurn(true)
                            }
                        } else if (firstPlayerTurnCellIndex == 6) {
                            if (!putFigure(cellsArray[2])) {
                                randomTurn(true)
                            }
                        } else if (firstPlayerTurnCellIndex == 8) {
                            if (!putFigure(cellsArray[0])) {
                                randomTurn(sideTurn = true)
                            }
                        } else {
                            randomTurn()
                        }
                    } else {
                        if (secondPlayerTurnCellIndex == 0 || secondPlayerTurnCellIndex == 2 || secondPlayerTurnCellIndex == 6 || secondPlayerTurnCellIndex == 8) {
                            if (secondPlayerTurnCellIndex == 0) {
                                randomTurn(true)
                            } else if (secondPlayerTurnCellIndex == 2) {
                                randomTurn(true)
                            } else if (secondPlayerTurnCellIndex == 6) {
                                randomTurn(true)
                            } else if (secondPlayerTurnCellIndex == 8) {
                                randomTurn(sideTurn = true)
                            } else {
                                randomTurn()
                            }
                        } else if (checkOppositeSecondPlayerTurn()) {
                            randomTurn(cornerTurn = true)
                        } else {
                            when {
                                firstPlayerTurnCellIndex == 1 && secondPlayerTurnCellIndex == 3 -> putFigure(cellsArray[0])
                                firstPlayerTurnCellIndex == 1 && secondPlayerTurnCellIndex == 5 -> putFigure(cellsArray[2])
                                firstPlayerTurnCellIndex == 3 && secondPlayerTurnCellIndex == 1 -> putFigure(cellsArray[0])
                                firstPlayerTurnCellIndex == 3 && secondPlayerTurnCellIndex == 7 -> putFigure(cellsArray[6])
                                firstPlayerTurnCellIndex == 5 && secondPlayerTurnCellIndex == 1 -> putFigure(cellsArray[2])
                                firstPlayerTurnCellIndex == 5 && secondPlayerTurnCellIndex == 7 -> putFigure(cellsArray[8])
                                firstPlayerTurnCellIndex == 7 && secondPlayerTurnCellIndex == 3 -> putFigure(cellsArray[6])
                                firstPlayerTurnCellIndex == 7 && secondPlayerTurnCellIndex == 5 -> putFigure(cellsArray[8])
                            }
                        }
                    }
                } else {
                    if (field[0] == CellType.NOTHING) {
                        putFigure(cellsArray[0])
                    } else if (field[2] == CellType.NOTHING) {
                        putFigure(cellsArray[2])
                    } else if (field[6] == CellType.NOTHING) {
                        putFigure(cellsArray[6])
                    } else if (field[8] == CellType.NOTHING) {
                        putFigure(cellsArray[8])
                    } else {
                        randomTurn()
                    }
                }
            }
        }
    }
}

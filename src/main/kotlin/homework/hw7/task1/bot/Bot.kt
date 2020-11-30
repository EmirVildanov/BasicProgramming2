package homework.hw7.task1.bot

import javafx.beans.property.SimpleIntegerProperty

interface Bot {
    fun makeTurn(): SimpleIntegerProperty
}

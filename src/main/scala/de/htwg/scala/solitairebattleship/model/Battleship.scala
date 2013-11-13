package de.htwg.scala.solitairebattleship.model

class Battleship(val genGrid:Grid, val ships:List[Ship]) {
	val userGrid = new Grid(genGrid.size)
}
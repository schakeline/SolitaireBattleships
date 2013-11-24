package de.htwg.scala.solitairebattleship.controller

import de.htwg.scala.solitairebattleship.model._

class GameController {
	private var _model:Battleship = null;
	private def model_=(model:Battleship) {_model = model}
	def model = _model

	def createGameWithSize(theSize:Int):Battleship = {
		
		// game generation code goes here
		// TODO: generate game for size
		var grid = new Grid(theSize)
		var ships:List[Ship] = dummyShips

		model = new Battleship(grid, ships)
		model
	}

	def dummyShips:List[Ship] = {
		var ships:List[Ship] = Nil
		for (i <- 0 until 10) {
			var s = i match {
				case x if x < 4 => 1
				case x if x < 7 => 2
				case x if x < 9 => 3
				case _ => 4
			}
			ships = (new Ship(i, s)) :: ships
		}
		ships
	}

}
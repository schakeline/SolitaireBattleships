package de.htwg.scala.solitairebattleship.controller

import de.htwg.scala.solitairebattleship.model._
import de.htwg.scala.solitairebattleship.util.Orientation._

class GameController {
	private var _model:Battleship = new Battleship
	private def model_=(model:Battleship) {_model = model}
	def model = _model

	def newGame(gridSize:Int = 10) {
		
		// game generation code goes here
		// TODO: generate game for size 

		model.genGrid = new Grid(gridSize)
		model.userGrid = new Grid(gridSize)
		model.ships = dummyShips // (ship count <= 10 | 0 <= id < 10)!!!

		model.placeShip(model.ships(2), 1, 1, Horizontal)
		model.placeShip(model.ships(4), 3, 4, Vertical)
	}

	// TODO: Delete fkt if generation is implemented !!!
	private def dummyShips:List[Ship] = {
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

	def placeShip(id:Int, x:Int, y:Int, orientation:Orientation) {
		
		try {
			// get ship
			var ship = model.getShipWithID(id) // return type is Option[Ship]
			model.placeShip(ship.get, x, y, orientation) // throws exception if no ship found
		} catch {
			case e:Exception => println("ERROR")// FIXME: view.showError(e)
		}


		// catch exceptions and pass to ui
		// if all ships are placed validate game
		// call uis error method
	}

	def removeShip(id:Int) {
		
		try {
			// get ship
			var ship = model.getShipWithID(id) // return type is Option[Ship]
			model.removeShip(ship.get) // throws exception if no ship found
		} catch {
			case e:Exception => println("ERROR")//FIXME: view.showError(e)
		}

	}

}
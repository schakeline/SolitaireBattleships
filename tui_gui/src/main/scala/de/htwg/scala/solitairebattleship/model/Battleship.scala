package de.htwg.scala.solitairebattleship.model

import de.htwg.scala.solitairebattleship.util.Observable
import de.htwg.scala.solitairebattleship.util.Orientation._

class Battleship() extends Observable {
	private var _userGrid:Grid = null
	private var _genGrid:Grid = null
	private var _ships:List[Ship] = null

	def userGrid = _userGrid
	def userGrid_=(grid:Grid) {
		if (grid != null) {
			_userGrid = grid
			notifyObservers
		}
	}

	def genGrid = _genGrid
	def genGrid_=(grid:Grid) {
		if (grid != null) {
			_genGrid = grid
			userGrid = new Grid(_genGrid.size)
			notifyObservers
		}
		
	}
	
	def ships = _ships
	def ships_=(ships:List[Ship]) {
		if (ships != null) {
			_ships = ships
			notifyObservers
		}
	}

  def getShipWithID(theID:Int) = ships.find(p => p.id == theID)

	def placeShip(theShip:Ship, x:Int, y:Int, orientation:Orientation) {
		userGrid.placeShip(theShip, x, y, orientation)

		notifyObservers
	}

	def removeShip(ship:Ship) {
		userGrid.removeShip(ship)
		notifyObservers
	}

	override def notifyObservers = {
		if (genGrid != null && userGrid != null && ships != null) super.notifyObservers
	}

	/* 
	 * @returns a orderd list of unset ships, beginning with the smallest ship.
	**/
	def getUnplacedShips:List[Ship] = {
		var unplacedShips = ships.toSet

		for (i <- 0 until userGrid.size; j <- 0 until userGrid.size) {
			var ship:Ship = userGrid.getCell(i, j)

			if (ship != null) {
				unplacedShips = (unplacedShips-ship)
			}
		}
		unplacedShips.toList.sortBy(x => (x.size, x.id))
	}

	def validateRowSum(r:Int) = {userGrid.getRowSum(r) == genGrid.getRowSum(r)}
	def validateColumnSum(c:Int) = {userGrid.getColumnSum(c) == genGrid.getColumnSum(c)}
}
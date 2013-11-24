package de.htwg.scala.solitairebattleship.model

class Battleship(val genGrid:Grid, val ships:List[Ship]) extends IBattleship {
  val userGrid = new Grid(genGrid.size)
  //private var _genGrid:Grid = null

  /* 
   *@returns a orderd list of unset ships, beginning with the smallest ship.
   */
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

  //def genGrid = _genGrid
  /*def genGrid_=(grid:Grid) {
    _genGrid = grid
    update
  }*/
  
  //def userGrid = _userGrid
  /*def userGrid_=(grid:Grid) {
    _userGrid = grid
    update
  }*/
  
  //def ships = _ships
  /*def ships_=(ships:List[Ship]) {
    _ships = ships
    update
  }*/
}
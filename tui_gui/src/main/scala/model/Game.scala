package de.htwg.scala.solitairebattleship.model

import de.htwg.scala.solitairebattleship.util.Observable
import de.htwg.scala.solitairebattleship.util.Orientation._

object Game extends Observable with IGame {
    
  private var userGrid:Grid = null
  private var _ships:List[Ship] = Nil
  private var _solution:IGrid = null
  

  def init(ships:List[Ship], solution:IGrid) {
    _ships = ships
    _solution = solution
    userGrid = new Grid(this.solution.size)

    notifyObservers
  }

  def gridSize:Int = solution.size
  def ships:List[Ship] = _ships
  def solution:IGrid = _solution

  /**
   * Returns the grid which should be shown the user to play the game.
   * @returns a VisibleGrid, which contains the user's placed ships and
   *          the solution's row and column sums.
   */
  def gameGrid:IGrid = {

    var gridArray:List[List[Ship]] = Nil
    for (i <- gridSize-1 to 0 by -1) { // reverse order because of list speed
      gridArray = userGrid.getRow(i) :: gridArray
    }

    var rowSums:List[Int] = Nil
    for (i <- gridSize-1 to 0 by -1) { // reverse order because of list speed
      rowSums = solution.getRowSum(i) :: rowSums
    }

    var columnSums:List[Int] = Nil
    for (i <- gridSize-1 to 0 by -1) {
      columnSums = solution.getColumnSum(i) :: columnSums
    }

    new VisibleGrid(gridArray, rowSums, columnSums)
  }

  def placeShip(theShip:Ship, x:Int, y:Int, orientation:Orientation) {
    userGrid.placeShip(theShip, x, y, orientation)
    notifyObservers
  }

  def removeShip(theShip:Ship) {
    userGrid.removeShip(theShip)
    notifyObservers
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

  def getShipWithID(theID:Int) = ships.find(p => p.id == theID)
  def validateRowSum(r:Int) = {userGrid.getRowSum(r) == solution.getRowSum(r)}
  def validateColumnSum(c:Int) = {userGrid.getColumnSum(c) == solution.getColumnSum(c)}
}
package de.htwg.scala.solitairebattleship.model

import de.htwg.scala.solitairebattleship.util.Observable
import de.htwg.scala.solitairebattleship.util.Orientation._
import scala.collection.immutable.ListSet

class Game(private val ships:List[Ship], val solution:IGrid) extends IGame {
    
  def gridSize = solution.size
  private var userGrid:Grid = new Grid(gridSize)

  /**
   * Returns the grid which should be shown the user to play the game.
   * @returns a VisibleGrid, which contains the user's placed ships and
   *          the solution's row and column sums.
   */
  def gameGrid:IGrid = {

    var gridArray:List[List[Option[Ship]]] = Nil
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
    userGrid = userGrid.placeShip(theShip, x, y, orientation)
    Model.notifyObservers
  }

  def removeShip(theShip:Ship) {
    userGrid = userGrid.removeShip(theShip)
    Model.notifyObservers
  }

  /* 
   * @returns a ordered list of unset ships, beginning with the smallest ship.
  **/
  def getUnplacedShips:List[Ship] = (ships.toSet--getPlacedShips).toList.sortBy(s => (s.size, s.id))

  def getPlacedShips:List[Ship] = {
    var placedShips = Set[Ship]()

    for (i <- 0 until userGrid.size; j <- 0 until userGrid.size) {
      userGrid.getCell(i, j) match{
        case Some(ship) => {placedShips = (placedShips + ship)}
        case _ => {}
      }
    }
    placedShips.toList.sortBy(x => x.id)
  }

  def getShips:List[Ship] = ships

  def getShipWithID(theID:Int):Ship = ships.find(p => p.id == theID).get
  
  def isValid():Boolean = {
    var valid:Boolean = getCollisions.isEmpty

    if (valid) {
      for (i <- 0 until gridSize) {
        if ( !(validateRowSum(i) && validateColumnSum(i)) ) return false
      }
    }
    valid
  }

  def validateRowSum(r:Int):Boolean = {userGrid.getRowSum(r) == solution.getRowSum(r)}
  def validateColumnSum(c:Int):Boolean = {userGrid.getColumnSum(c) == solution.getColumnSum(c)}
  def getCollisions():List[Tuple2[Int,Int]] = {Validator.validateNeighborhood(userGrid)}
}
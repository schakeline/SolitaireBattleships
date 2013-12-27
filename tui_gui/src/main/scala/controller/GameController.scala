package de.htwg.scala.solitairebattleship.controller

import scala.collection.immutable.ListSet
import de.htwg.scala.solitairebattleship.view.IView
import de.htwg.scala.solitairebattleship.model._
import de.htwg.scala.solitairebattleship.util.Orientation._


class GameController {

  private var views:ListSet[IView] = new ListSet()
  def registerView(theView:IView) {views = views + theView}
  def unregisterView(theView:IView) {views = views - theView}

  def newGame(gridSize:Int = 10) {
    if (gridSize < 3 || gridSize > 10) {
      throw new IllegalArgumentException()
    } else {
      val ships:List[Ship] = ShipFactory.getShips(gridSize)
      val grid = new GameGenerator(ships, gridSize).generateGrid
      Model.game = new Game(ships, grid)
    }
  }

  def placeShip(id:Int, x:Int, y:Int, orientation:Orientation) {
    // get ship
    var ship = Model.game.getShipWithID(id) // return type is Option[Ship]
    Model.game.placeShip(ship, x, y, orientation) // throws exception if no ship found
    
    if (Model.game.getUnplacedShips.isEmpty) {
      // check if valid
      if (Model.game.isValid) {
        views.foreach(v => v.showGameFinished)
        Model.game = null
      }
      else views.foreach(v => v.showValidationResult)
    }
  }

  def removeShip(id:Int) {
    // get ship
    var ship = Model.game.getShipWithID(id) // return type is Option[Ship]
    Model.game.removeShip(ship) // throws exception if no ship found
  }

  def showSolution {
    views.foreach(v => v.showSolution)
    Model.game = null
  }

}
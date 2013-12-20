package de.htwg.scala.solitairebattleship.controller

import de.htwg.scala.solitairebattleship.model._
import de.htwg.scala.solitairebattleship.util.Orientation._

class GameController {

  /*private var _model:IGame = new Game
  private def model_=(model:IGame) {_model = model}
  def model = _model
  */
  def newGame(gridSize:Int = 10) {
    
    val ships:List[Ship] = ShipFactory.getShips(gridSize)
    val grid = new GameGenerator(ships, gridSize).generateGrid
    
    Model.game = new Game(ships, grid)
  }

  def placeShip(id:Int, x:Int, y:Int, orientation:Orientation) {
    try {
      // get ship
      var ship = Model.game.getShipWithID(id) // return type is Option[Ship]
      Model.game.placeShip(ship, x, y, orientation) // throws exception if no ship found
    } catch {
      case e:Exception => println("ERROR")// FIXME: view.showError(e)
    }


    // catch exceptions and pass to ui
    // if all ships are placed validate game
    // call uis error method

    // FIXME: Validation 2nd if-block not working
    if (Model.game.getUnplacedShips.isEmpty) {
      if (Validator.validateNeighborhood(Model.game.gameGrid))
    
      Validator.validateRowSums(Model.game.gameGrid, Model.game.solution).foreach(r => print(r))

      Validator.validateColumnSums(Model.game.gameGrid, Model.game.solution).foreach(c => print(c))
    }
  }

  def removeShip(id:Int) {
    try {
      // get ship
      var ship = Model.game.getShipWithID(id) // return type is Option[Ship]
      Model.game.removeShip(ship) // throws exception if no ship found
    } catch {
      case e:Exception => println("ERROR")//FIXME: view.showError(e)
    }
  }

}
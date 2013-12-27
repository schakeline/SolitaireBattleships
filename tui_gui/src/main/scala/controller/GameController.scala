package de.htwg.scala.solitairebattleship.controller

import de.htwg.scala.solitairebattleship.model._
import de.htwg.scala.solitairebattleship.util.Orientation._

class GameController {
  private var _model:Battleship = new Battleship
  private def model_=(model:Battleship) {_model = model}
  def model = _model

  def newGame(gridSize:Int = 10) {
    model.ships = ShipFactory.getShips(gridSize)
    model.genGrid = new GameGenerator(model.ships, gridSize).generateGrid
    model.userGrid = new Grid(gridSize)
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

    if (model.getUnplacedShips.isEmpty) {
      if (Validator.validateNeighborhood(model.userGrid))
      

      Validator.validateRowSums(model.userGrid, model.genGrid).foreach(r => print(r))

      Validator.validateColumnSums(model.userGrid, model.genGrid).foreach(c => print(c))

    }
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
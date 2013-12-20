package de.htwg.scala.solitairebattleship.model

import de.htwg.scala.solitairebattleship.util.Orientation._


trait IGame {
  def gridSize:Int
  val solution:IGrid
  def gameGrid:IGrid

  def placeShip(theShip:Ship, x:Int, y:Int, orientation:Orientation)
  def removeShip(theShip:Ship)
  def getUnplacedShips:List[Ship]
}
package de.htwg.scala.solitairebattleship.model

import de.htwg.scala.solitairebattleship.util.Orientation._


trait IGame {
  def gridSize:Int
  def solution:IGrid
  def gameGrid:IGrid

  def init(ships:List[Ship], solution:IGrid)
  def placeShip(theShip:Ship, x:Int, y:Int, orientation:Orientation)
  def removeShip(theShip:Ship)
  def getUnplacedShips:List[Ship]
}
package de.htwg.scala.solitairebattleship.model

import de.htwg.scala.solitairebattleship.util.Orientation._


trait IGame {
  def gridSize:Int
  val solution:IGrid
  def gameGrid:IGrid

  def placeShip(theShip:Ship, x:Int, y:Int, orientation:Orientation)
  def removeShip(theShip:Ship)
  def getUnplacedShips:List[Ship]
  def getPlacedShips:List[Ship]
  def getShips:List[Ship]

  def validateRowSum(r:Int):Boolean
  def validateColumnSum(c:Int):Boolean
  def getCollisions():List[Tuple2[Int,Int]]
}
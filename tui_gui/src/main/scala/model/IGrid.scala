package de.htwg.scala.solitairebattleship.model

trait IGrid {
  def size:Int
  def getCell(x:Int, y:Int):Ship
  def getRowSum(r:Int):Int
  def getColumnSum(c:Int):Int
}
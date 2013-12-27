package de.htwg.scala.solitairebattleship.model

case class VisibleGrid( 
  private val gridArray:List[List[Ship]], 
  private val rowSums:List[Int], 
  private val columnSums:List[Int]) extends IGrid {
  
  def size = gridArray.size
  def getCell(x:Int, y:Int):Ship = gridArray(y)(x)
  def getRowSum(r:Int):Int = rowSums(r)
  def getColumnSum(c:Int):Int = columnSums(c)
}
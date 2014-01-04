package de.htwg.scala.solitairebattleship.model

import de.htwg.scala.solitairebattleship.model.exception.ShipCollisionException
import de.htwg.scala.solitairebattleship.util.Orientation._
import de.htwg.scala.solitairebattleship.util.Position


class Grid (val size:Int) extends IGrid {
private val gridArray = Array.ofDim[Ship](size, size)
  
  if (size < 2) throw new IllegalArgumentException
  
  def placeShip(theShip:Ship, position:Position):Grid = {
    placeShip(theShip,position.x, position.y, position.orientation)
  }
  
  def placeShip(theShip:Ship, x:Int, y:Int, orientation:Orientation):Grid = {
      
    if (theShip == null) throw new IllegalArgumentException
    if (x < 0 || x >= size || y < 0 || y >= size) throw new IndexOutOfBoundsException
    
    var newGrid = this.copy()
    newGrid = newGrid.removeShip(theShip)
    
    // List with cells occupied by ship
    var fields = (x, y) :: Nil
    
    for (i <- 0 until theShip.size) {
      if (orientation == Horizontal) fields = (x+i, y) :: fields
      else fields = (x, y+i) :: fields
    }
    
    if (!newGrid.cellInGrid(fields(0)))
      throw new ShipCollisionException("With border.")
    else if (!newGrid.cellsFree(fields))
      throw new ShipCollisionException("With another ship.")
    else
      fields.foreach(f => newGrid.gridArray(f._2)(f._1) = theShip)

    newGrid
  }
  
  private def cellInGrid(cell:Tuple2[Int, Int]):Boolean = (cell._1 >= 0 && cell._1 < size && cell._2 >= 0 && cell._2 < size)
  
  private def cellsFree(fields:List[Tuple2[Int, Int]]):Boolean = fields.filterNot(f => gridArray(f._2)(f._1) == null).isEmpty
    
  def removeShip(ship:Ship):Grid = {
    if (ship == null) throw new IllegalArgumentException
    
    val newGrid = this.copy()

    for (y <- 0 until newGrid.size; x <- 0 until newGrid.size) { 
    if(newGrid.gridArray(y)(x) == ship)
      newGrid.gridArray(y)(x) = null
    }
    newGrid
  }
      
  def getRowSum(row:Int):Int = {   
    var sum = 0
    for(x <- 0 until size){
      if((gridArray(row)(x)) != null) 
        sum += 1
    }
    sum
  }
  
  def getColumnSum(column:Int):Int = {
    var sum = 0
    for(y <- 0 until size){
      if((gridArray(y)(column)) != null) 
        sum += 1
    }
    sum
  }
  
  def getCell(x:Int, y:Int):Ship = {
    gridArray(y)(x)
  }

  def copy():Grid = {
    var nGrid = new Grid(size)
    for(x <- 0 until size; y <- 0 until size)
      nGrid.gridArray(x)(y) = this.gridArray(x)(y)
    
    nGrid
  }
  
  def getRow(r:Int):List[Ship] = gridArray(r).toList
  
 
}
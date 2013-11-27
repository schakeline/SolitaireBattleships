package de.htwg.scala.solitairebattleship.model

import de.htwg.scala.solitairebattleship.model.exception.ShipCollisionException
import de.htwg.scala.solitairebattleship.util.Orientation._


class Grid (val size:Int) {
private val gridArray = Array.ofDim[Ship](size, size)
  
  if (size < 2) throw new IllegalArgumentException
  
	
  def placeShip(theShip:Ship, x:Int, y:Int, orientation:Orientation) = {
		  
		if (theShip == null) throw new IllegalArgumentException
		if (x < 0 || x >= size || y < 0 || y >= size) throw new IndexOutOfBoundsException
		
		removeShip(theShip)
		
		// List with cells occupied by ship
		var fields = (x, y) :: Nil
		
		for (i <- 0 until theShip.size) {
			if (orientation == Horizontal) fields = (x+i, y) :: fields
			else fields = (x, y+i) :: fields
		}
		
		if (!cellInGrid(fields(0)))
		  throw new ShipCollisionException("With border.")
		else if (!cellsFree(fields))
			throw new ShipCollisionException("With another ship.")
		else
		  fields.foreach(f => gridArray(f._2)(f._1) = theShip)
  }
  
  private def cellInGrid(cell:Tuple2[Int, Int]):Boolean = (cell._1 >= 0 && cell._1 < size && cell._2 >= 0 && cell._2 < size)
  
  private def cellsFree(fields:List[Tuple2[Int, Int]]):Boolean = fields.filterNot(f => gridArray(f._2)(f._1) == null).isEmpty
    
  def removeShip(ship:Ship) = {
    if (ship == null) throw new IllegalArgumentException
    for (y <- 0 until size; x <- 0 until size) { 
	  if(gridArray(y)(x) == ship)
		  gridArray(y)(x) = null
    }
  }
  
  def getPosOfShip(theShip:Ship):Tuple2[Int, Int] = {
    if (theShip == null) throw new IllegalArgumentException
    
    // look for first cell that is occupied by the ship
    for (y <- 0 until size; x <- 0 until size) {
    	if (gridArray(y)(x) == theShip) {
    	  return (x,y) // stop if first occupied by ship was found
    	}
    }
    (-1,-1)
  }
    
  def getRowSum(row:Int) = {
    var sum = 0
    for(x <- 0 until size){
      if((gridArray(row)(x)) != null) 
        sum += 1
    }
    sum
  }
  
  def getColumnSum(column:Int) = {
    var sum = 0
    for(y <- 0 until size){
      if((gridArray(y)(column)) != null) 
        sum += 1
    }
    sum
  }
  
  def getCell(x:Int, y:Int) = {
    gridArray(y)(x)
  }
  
}
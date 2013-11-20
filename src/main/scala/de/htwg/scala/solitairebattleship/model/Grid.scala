package de.htwg.scala.solitairebattleship.model

import de.htwg.scala.solitairebattleship.model.exception.ShipCollisionException

object Orientation extends Enumeration {
	type Orientation = Value
	val Horizontal, Vertical = Value
}

import de.htwg.scala.solitairebattleship.model.Orientation._

class Grid (val size:Int) {
  val gridArray = Array.ofDim[Ship](size, size)
  
  if (size < 2) throw new IllegalArgumentException
  
	
  def placeShip(theShip:Ship, x:Int, y:Int, orientation:Orientation) = {
		  
		if (theShip == null) throw new IllegalArgumentException
		if (x < 0 || x >= size || y < 0 || y >= size) throw new IndexOutOfBoundsException
		
		removeShip(theShip)
		
		// List with cells occupied by ship
		var fields = (x, y) :: Nil
		
		for (i <- 0 until theShip.size) {
			if (orientation == Orientation.Horizontal) fields = (x+i, y) :: fields
			else fields = (x, y+i) :: fields
		}
		
		if (!cellInGrid(fields(0)))
		  throw new ShipCollisionException("With border.")
		else if (!cellsFree(fields))
			throw new ShipCollisionException("With another ship.")
		else
		  fields.foreach(f => gridArray(f._1)(f._2) = theShip)
  }
  
  private def cellInGrid(cell:Tuple2[Int, Int]):Boolean = (cell._1 >= 0 && cell._1 < size && cell._2 >= 0 && cell._2 < size)
  
  private def cellsFree(fields:List[Tuple2[Int, Int]]):Boolean = fields.filterNot(f => gridArray(f._1)(f._2) == null).isEmpty
    
  def removeShip(ship:Ship) = {
    if (ship == null) throw new IllegalArgumentException
    for (x <- 0 until size; y <- 0 until size) { 
	  if(gridArray(x)(y) == ship)
		  gridArray(x)(y) = null
    }
  }
  
  def getPosOfShip(theShip:Ship):Tuple2[Int, Int] = {
    if (theShip == null) throw new IllegalArgumentException
    
    // look for first cell that is occupied by the ship
    for (y <- 0 until size; x <- 0 until size) {
    	if (gridArray(x)(y) == theShip) {
    	  return (x,y) // stop if first occupied by ship was found
    	}
    }
    (-1,-1)
  }
    
  def getRowSum(row:Int) = {
    var sum = 0
    for(i <- 0 until size){
      if((gridArray(i)(row)) != null) 
        sum += 1
    }
    sum
  }
  
  def getColumnSum(column:Int) = {
    var sum = 0
    for(i <- 0 until size){
      if((gridArray(column)(i)) != null) 
        sum += 1
    }
    sum
  }
  
}
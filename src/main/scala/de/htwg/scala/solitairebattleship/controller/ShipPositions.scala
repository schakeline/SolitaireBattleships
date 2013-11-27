import de.htwg.scala.solitairebattleship.model._
import de.htwg.scala.solitairebattleship.util.Orientation._

package de.htwg.scala.solitairebattleship.controller{
	
	class ShipPositions(val ship:Ship,  val gridSize:Int){
	  
	  if(ship == null) throw new IllegalArgumentException()
		  
	  var possiblePositions:List[Position] = Nil  
	  getPossiblePositions(ship)
		  
	  private def getPossiblePositions(ship:Ship) = {
		 // horizontal placement
		  for(x <- 0 to gridSize - ship.size){
		    for(y <- 0 until gridSize)
		      possiblePositions = new Position(x,y,Horizontal):: possiblePositions
		  }
		  //vertical placement
		  for(x <- 0 until gridSize){
		    for(y <- 0 to gridSize - ship.size)
		      possiblePositions = new Position(x,y,Vertical)::possiblePositions
		  }
		}
	  
	  def rmPosition(x:Int, y:Int, orientation:Orientation) = {
	    possiblePositions = possiblePositions.filterNot(p => p.x == x 
	    													 && p.y == y 
	    													 && p.orientation == orientation)
	  }
	}
}


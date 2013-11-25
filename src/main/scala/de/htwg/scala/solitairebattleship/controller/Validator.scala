import de.htwg.scala.solitairebattleship.model._

package de.htwg.scala.solitairebattleship.controller{

	object Validator {
	  def validateNeighborhood(theGrid:Grid):Boolean  = {
		  var valid = false; 
		  
		  for(x <- 0 until theGrid.size -1 ){
			   for(y <- 0 until theGrid.size -1){
				   var hotSpot = theGrid.getCell(x,y)
				   var eastNeighbor = theGrid.getCell(x+1,y)
				   var southNeighbor = theGrid.getCell(x,y+1)
				   var southEastNeighbor = theGrid.getCell(x+1,y+1)
				   
				   if(hotSpot != null){
				     if(hotSpot != eastNeighbor && eastNeighbor != null) 
				       return false
				     
				     if(hotSpot != southNeighbor && southNeighbor != null)
				       return false
				       
				     if(hotSpot != southEastNeighbor && southEastNeighbor != null)
				       return false
				   }
			   }
		    }
		  return true
		}
	}
}
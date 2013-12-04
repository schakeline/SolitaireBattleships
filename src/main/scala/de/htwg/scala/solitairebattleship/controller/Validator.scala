package de.htwg.scala.solitairebattleship.controller

import de.htwg.scala.solitairebattleship.model._


object Validator {
  def validateNeighborhood(theGrid:Grid):Boolean  = {
	  var valid = false; 
	  
	  for(x <- 0 until theGrid.size -1; y <- 0 until theGrid.size -1){
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

		       // FIXME: validation for SW is missing.
		   }
	    }
	  return true
	}

	def validateRowSums(userGrid:Grid, refGrid:Grid):List[Int] = {
		
		var result:List[Int] = Nil

		for (y <- 0 until userGrid.size) {
			if (userGrid.getRowSum(y) != refGrid.getRowSum(y)) {
				result = result :+ y
			}
		}
		result
	}

	def validateColumnSums(userGrid:Grid, refGrid:Grid):List[Int] = {
		var result:List[Int] = Nil

		for (x <- 0 until userGrid.size) {
			if (userGrid.getColumnSum(x) != refGrid.getColumnSum(x)) {
				result = result :+ x
			}
		}
		result
	}

}
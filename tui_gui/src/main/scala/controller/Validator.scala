package de.htwg.scala.solitairebattleship.controller

import de.htwg.scala.solitairebattleship.model._


object Validator {
  def validateNeighborhood(theGrid:IGrid):Boolean  = {
    var valid = false; 
    
    for(x <- 0 until theGrid.size; y <- 0 until theGrid.size){
       var hotSpot = theGrid.getCell(x,y)
             
       if(hotSpot != null){
         
         if(x < theGrid.size -1 ){
           var eastNeighbor = theGrid.getCell(x+1,y)
           //if(hotSpot != eastNeighbor && eastNeighbor != null) return false
           if(CollisionBetween(hotSpot, eastNeighbor)) return false
         }
         
         if(y < theGrid.size -1 ) {
           var southNeighbor = theGrid.getCell(x,y+1)
           //if(hotSpot != southNeighbor && southNeighbor != null) return false
           if(CollisionBetween(hotSpot, southNeighbor)) return false
         }
         
         if(x < theGrid.size -1 && y < theGrid.size -1){
           var southEastNeighbor = theGrid.getCell(x+1,y+1)
           //if(hotSpot != southEastNeighbor && southEastNeighbor != null)return false
           if(CollisionBetween(hotSpot, southEastNeighbor)) return false
         }
           
         if(x >= 1 && y < theGrid.size - 1) {
           var southWestNeighbor = theGrid.getCell(x - 1, y + 1)
           //if(hotSpot != southWestNeighbor && southWestNeighbor != null) return false
           if(CollisionBetween(hotSpot,southWestNeighbor)) return false
         }
       }
      }
    return true
  }

  private def CollisionBetween(hotspot:Ship, neighbor:Ship):Boolean = {
    if(hotspot != neighbor && neighbor != null) return true
    else false
  }
  
  // FIXME: validation not working because userGrid returns not the real rowSum
  def validateRowSums(userGrid:IGrid, refGrid:IGrid):List[Int] = {
    
    var result:List[Int] = Nil

    for (y <- 0 until userGrid.size) {
      if (userGrid.getRowSum(y) != refGrid.getRowSum(y)) {
        result = result :+ y
      }
    }
    result
  }

  // FIXME: validation not working because userGrid returns not the real columnSum
  def validateColumnSums(userGrid:IGrid, refGrid:IGrid):List[Int] = {
    var result:List[Int] = Nil

    for (x <- 0 until userGrid.size) {
      if (userGrid.getColumnSum(x) != refGrid.getColumnSum(x)) {
        result = result :+ x
      }
    }
    result
  }

}

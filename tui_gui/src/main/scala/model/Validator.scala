package de.htwg.scala.solitairebattleship.model

import de.htwg.scala.solitairebattleship.model._


object Validator {
  
  def validateNeighborhood(theGrid:IGrid):List[Tuple2[Int,Int]]  = {
    
    var collisions:List[Tuple2[Int,Int]] = Nil 
    
    for (x <- 0 until theGrid.size; y <- 0 until theGrid.size){
      var hotSpot = theGrid.getCell(x,y)
             
      if (hotSpot != null){
         
        if (x < theGrid.size -1 ){
          var eastNeighbor = theGrid.getCell(x+1,y)
          if (CollisionBetween(hotSpot, eastNeighbor)) {
            collisions = (x,y) :: (x+1,y) :: collisions
          }
        }
         
        if (y < theGrid.size -1 ) {
          var southNeighbor = theGrid.getCell(x,y+1)
          if (CollisionBetween(hotSpot, southNeighbor)){
            collisions = (x,y) :: (x,y+1) :: collisions
          }
        }
         
        if (x < theGrid.size -1 && y < theGrid.size -1){
          var southEastNeighbor = theGrid.getCell(x+1,y+1)
          if (CollisionBetween(hotSpot, southEastNeighbor)){
            collisions = (x,y) :: (x+1,y+1) :: collisions
          }
        }
           
        if (x >= 1 && y < theGrid.size - 1) {
          var southWestNeighbor = theGrid.getCell(x - 1, y + 1)
          if (CollisionBetween(hotSpot,southWestNeighbor)){
            collisions = (x,y) :: (x-1,y+1) :: collisions
          }
        }    
      }
    }
    collisions
  }

  private def CollisionBetween(hotspot:Ship, neighbor:Ship):Boolean = {
    if(hotspot != neighbor && neighbor != null) return true
    else false
  }
  
}

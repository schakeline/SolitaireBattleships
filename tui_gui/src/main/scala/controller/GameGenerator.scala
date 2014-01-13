package de.htwg.scala.solitairebattleship.controller

import de.htwg.scala.solitairebattleship.model._
import de.htwg.scala.solitairebattleship.util.Orientation._
import scala.util._
import de.htwg.scala.solitairebattleship.util.Position

class GameGenerator (val allShips:List[Ship],val gameSize:Int){
  
  var grid = new Grid(gameSize)
  var solutions:List[Grid] = Nil 
  var rand = new Random()
  var solutionFound = false
  
  private def shipIDs() = {
    val ids = allShips.map(b => b.id)
    ids.sorted
  }
  
  def generateGrid():Grid = 
  {
    placeShips(shipIDs, grid, allShips)
    solutions(0)
  }
  
  /*def printGrid(theGrid:Grid):Unit = {
    for( x <- 0 until theGrid.size){
      for(y <- 0 until theGrid.size){
        print(theGrid.getCell(y, x) + "|")
      }
      print("\n ------------------------------ \n")
    }
      
  }*/
  
  
  def placeShips(shipIDs:List[Int], grid:Grid, ships:List[Ship]):Unit ={
  
  if(solutionFound) return
    
  var theShip:Ship = null 
    //every id is set
    if(shipIDs.size==0){
      solutions = grid :: solutions
      solutionFound = true
    }
    else{
      theShip = ships.filter(s => s.id == shipIDs(0))(0)
      
      //generate a list with all positions
      var positions = getPossiblePositions(grid.size, theShip.size)
    
      val size = positions.size
      for (i <- 0 until size) {
        var tmpGrid = grid.copy()
        val tmpPos = positions(rand.nextInt(size - i))
        positions = positions.filter(p => p != tmpPos)
      
        //iterate over all positions
        var pos:Position = new Position(tmpPos._1,tmpPos._2,Vertical)
        if (tmpPos._3 == 0){pos = new Position(tmpPos._1,tmpPos._2,Horizontal)}
                   
        if (cellsAreEmpty(pos,theShip,tmpGrid)){       
          var tmpIDs:List[Int] = Nil        
            
          //println("x: " + pos.x + "y: " + pos.y+ pos.orientation+ " ship: "+ theShip.id)
          //printGrid(tmpGrid)
          
          //the Cell is empty so place the ship         
          tmpGrid = tmpGrid.placeShip(theShip, pos)         
            
          if (Validator.validateNeighborhood(tmpGrid).isEmpty == false){
            //Bad Neighborhood, we need to remove the ship
            //println("remove it becouse of: " + Validator.validateNeighborhood(tmpGrid))
            tmpGrid = tmpGrid.removeShip(theShip)
            tmpIDs = shipIDs
          }
          else{
            //ship is placed so place the next ship. not set theShip twice  
            //println("Placed")
            var tmpIDs = shipIDs.filter(_ != theShip.id) 
            placeShips(tmpIDs,tmpGrid,ships)
          } 
        }
      }
    }
  } 
  
  private def getPossiblePositions(girdSize:Int, shipSize:Int):List[Tuple3[Int,Int,Int]] = {
    var positions:List[Tuple3[Int,Int,Int]] = Nil
      for(x <- 0 until girdSize - shipSize + 1;
          y <- 0 until girdSize - shipSize + 1;
          o <- 0 to 1){
        positions = new Tuple3(x,y,o) :: positions
      }
    positions
  }
  
  private def cellsAreEmpty(position:Position, ship:Ship, grid:Grid):Boolean = {        
    val fields = grid.getUsedCells(ship,position.x,position.y,position.orientation)
    grid.cellsFree(fields)
  } 
}

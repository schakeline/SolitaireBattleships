package de.htwg.scala.solitairebattleship.controller

import de.htwg.scala.solitairebattleship.model._
import de.htwg.scala.solitairebattleship.util.Orientation._
import scala.util._
import de.htwg.scala.solitairebattleship.util.Position

class GameGenerator (val allShips:List[Ship],val gameSize:Int){
  
  var allShipPositions:List[ShipPositions] = Nil 
  var grid = new Grid(gameSize)
  var solutions:List[Grid] = Nil 
  var rand = new Random()
  var calls = 0
  var solutionFound = false
  
  allShips.foreach(f => {allShipPositions = new ShipPositions(f, gameSize) :: allShipPositions})
  allShipPositions =  allShipPositions.sortBy(b => b.ship.size)
  
  private def shipIDs() = {
    val ids = allShipPositions.map(b => b.ship.id)
    ids.sorted
  }
  
  def generateGrid():Grid = 
  {
    placeShips(shipIDs, grid, allShips)
    println("Number of Solutions: " + solutions.size)
    println("Calls:" + calls)
    printGrid(solutions(0))
    return solutions(0)
  }
  
  def printGrid(theGrid:Grid) = {
    for( x <- 0 until theGrid.size){
      for(y <- 0 until theGrid.size){
        print(theGrid.getCell(y, x) + "|")
      }
      print("\n ------------------------------ \n")
    }
      
  }
  
  
  def placeShips(shipIDs:List[Int], grid:Grid, ships:List[Ship]):Unit ={
  
  if(solutionFound) return
    
  calls = calls + 1
  var theShip:Ship = null 
    //every id is set
    if(shipIDs.size==0){
      solutions = grid :: solutions
      solutionFound = true
    }
    else{
      theShip = ships.filter(s => s.id == shipIDs(0))(0)
      
      //generate a list with all positions
      var positions:List[Tuple3[Int,Int,Int]] = Nil
      for(x <- 0 until grid.size -theShip.size + 1; y <- 0 until grid.size-theShip.size + 1; o <- 0 to 1)
        positions = new Tuple3(x,y,o) :: positions
    
      val size = positions.size
      for (i <- 0 until size) {
        var tmpGrid = grid.copy()
        val tmpPos = positions(rand.nextInt(size - i))
        positions = positions.filter(p => p != tmpPos)
      
        //iterate over all positions
        var pos:Position = null;
        if (tmpPos._3 == 0)
          pos = new Position(tmpPos._1,tmpPos._2,Horizontal)
        else pos = new Position(tmpPos._1,tmpPos._2,Vertical)
                    
        //print("pos:" + pos.x + "\\" + pos.y + "\t ship: " +theShip.id + " | ")
          
        if (CellsAreEmpty(pos,theShip,grid)){       
          var tmpIDs:List[Int] = Nil        
            
          //the Cell is empty so place the ship
          tmpGrid = tmpGrid.placeShip(theShip, pos)         
            
          if (Validator.validateNeighborhood(tmpGrid) == false){
            //Bad Neighborhood, we need to remove the ship
            tmpGrid = tmpGrid.removeShip(theShip)
            tmpIDs = shipIDs
          }
          else{
            //ship is placed so place the next ship. not set theShip twice  
            var tmpIDs = shipIDs.filter(_ != theShip.id) 
            placeShips(tmpIDs,tmpGrid,ships)
          } 
        }
      }
    }
  } 
  
  private def CellsAreEmpty(position:Position, ship:Ship, grid:Grid):Boolean = {
    var content:Ship = null;
    for(i <- 0 until ship.size)
    {
      //the ship is not in the grid
      if(position.x +i >= grid.size || position.y +i >= grid.size)
        return false
      
      if(position.orientation == Horizontal)
         content = grid.getCell(position.x +i, position.y)
      else content = grid.getCell(position.x,position.y + i)
      
      if(content != null) return false
    }
    return true;
  }
 
}
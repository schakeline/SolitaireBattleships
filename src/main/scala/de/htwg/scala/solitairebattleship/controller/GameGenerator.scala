package de.htwg.scala.solitairebattleship.controller

import de.htwg.scala.solitairebattleship.model._
import de.htwg.scala.solitairebattleship.model.Orientation._
import scala.util._

class GameGenerator (val allShips:List[Ship],val gameSize:Int){
	
	var allShipPositions:List[ShipPositions] = Nil 
	var grid = new Grid(gameSize)
	var solutions:List[Grid] = Nil 
	var rand = new Random()
	
	allShips.foreach(f => {allShipPositions = new ShipPositions(f, gameSize) :: allShipPositions})
	allShipPositions =  allShipPositions.sortBy(b => b.ship.size)
	
	private def shipIDs() = {
	  val ids = allShipPositions.map(b => b.ship.id)
	  ids.sorted
	}
	
	def generateGrid():Grid = 
	{
	  placeShips(shipIDs, grid, allShipPositions)
	  return solutions(0)
	}
	
	
	
	def placeShips(shipIDs:List[Int], grid:Grid,possiblePositions:List[ShipPositions]):Unit ={
  
  	//print debug infos
  	print("\nnot placed:")
  	shipIDs.foreach(print(_))
  
  	//every id is set
  	if(shipIDs.size==0){
  		solutions = grid :: solutions
  	}
  	else{
  		//get possible positions for the next ship
  		val theShip = possiblePositions.map(p => p.ship).filter(_.id == shipIDs(0))(0)
  		val tmp = possiblePositions.filter(p => p.ship.id == shipIDs(0))
  		if(tmp.size != 1)
  			throw new Exception("Id is not unique")
  		
  		val nextShipPositions = tmp(0).possiblePositions
  		if(nextShipPositions.size == 0)
  			return
  		
  		
  		val r = rand.nextInt(nextShipPositions.size-1)
  		val x = nextShipPositions(r).x
  		val y = nextShipPositions(r).y
  		val or = nextShipPositions(r).orientation
  		
  		//delete
  		val tmpPos = possiblePositions.filterNot(_ == nextShipPositions(r))
			
  		
  		print(" Pos: "+ x + "\\" + y)
  		
  		if(grid.getCell(x, y) == null)
  		{
  			//the Cell is empty so place the ship
  			grid.placeShip(theShip, x, y, or)
  			var tmpIds = shipIDs.filter(p => p!= shipIDs(0))
  				
  			if(Validator.validateNeighborhood(grid) == false)
  			{
  				//Bad Neighborhood, we need to remove the ship
  				grid.removeShip(theShip)
  				//ship is not placed. put it into the list of not set ships
  				tmpIds = theShip.id :: tmpIds
  				return
  			}
  			
  			
  			placeShips(tmpIds,grid,tmpPos)
  		}
  		else
  		{
  			placeShips(shipIDs, grid, tmpPos)
  		}

  	}
  }  
}
	
	
	
	
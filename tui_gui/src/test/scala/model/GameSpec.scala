package de.htwg.scala.solitairebattleship.model

import org.scalatest.FlatSpec
import org.scalatest.Matchers
import scala.swing.Orientable
import de.htwg.scala.solitairebattleship.util.Orientation


class GameSpec extends FlatSpec with Matchers {
  
  private def init() = {
    val ships = new Ship(0,1) :: new Ship(1,1) :: Nil
    var solution = new Grid(4)
    
    solution = solution.placeShip(ships(0),0,0,Orientation.Horizontal)
    solution = solution.placeShip(ships(1),3,3,Orientation.Horizontal)
    
    new Game(ships,solution)
    
  }
  
  "In a new game" should "no ship should be set" in {
    val newGame = init()
    
    newGame.getPlacedShips.size should be(0)
    newGame.getUnplacedShips.size should be(2)
    newGame.getShips.size should be(2)
  }
  
  "getShipWithID" should "return the correct ship" in {
    val ships = new Ship(0,1) :: new Ship(1,1) :: Nil
    var solution = new Grid(4)
    
    solution = solution.placeShip(ships(0),0,0,Orientation.Horizontal)
    solution = solution.placeShip(ships(1),3,3,Orientation.Horizontal)
    
    val newGame = new Game(ships,solution)
    
    newGame.getShipWithID(0) should be(ships(0))
    an [NoSuchElementException] should be thrownBy newGame.getShipWithID(5)
  }
  
  "isValid" should "validate a game" in {
    val ships = new Ship(0,1) :: new Ship(1,1) :: Nil
    var solution = new Grid(4)
    
    solution = solution.placeShip(ships(0),0,0,Orientation.Horizontal)
    solution = solution.placeShip(ships(1),3,3,Orientation.Horizontal)
    
    val newGame = new Game(ships,solution)
    
    newGame.isValid should be(false)
    
    newGame.placeShip(ships(0), 3, 3, Orientation.Horizontal)
    newGame.placeShip(ships(1), 0, 0, Orientation.Horizontal)
    
    newGame.isValid should be(true)
  }
  
  
  "Placing ships" should "be possible" in {
    val ships = new Ship(0,1) :: new Ship(1,1) :: Nil
    var solution = new Grid(4)
    
    solution = solution.placeShip(ships(0),0,0,Orientation.Horizontal)
    solution = solution.placeShip(ships(1),3,3,Orientation.Horizontal)
    
    val newGame = new Game(ships,solution)
    newGame.placeShip(ships(0), 0, 0, Orientation.Horizontal)
    
    newGame.getPlacedShips.size should be(1)
    newGame.getUnplacedShips.size should be(1)
    newGame.getShips.size should be(2)
  }
  
  "Remove a ship" should "be possible" in {
    val ships = new Ship(0,1) :: new Ship(1,1) :: Nil
    var solution = new Grid(4)
    
    solution = solution.placeShip(ships(0),0,0,Orientation.Horizontal)
    solution = solution.placeShip(ships(1),3,3,Orientation.Horizontal)
    
    val newGame = new Game(ships,solution)
    newGame.placeShip(ships(0), 0, 0, Orientation.Horizontal)
    
    newGame.getUnplacedShips.size should be(1)
    
    newGame.removeShip(ships(0))
    
    newGame.getUnplacedShips.size should be(2)
  }
  
  "gameGrid" should "be an IGrid with corrct sums" in {
    val grid = init().gameGrid
    
    grid.getColumnSum(0) should be(1)
    grid.getColumnSum(1) should be(0)
    grid.getColumnSum(2) should be(0)
    grid.getColumnSum(3) should be(1)
    
    grid.getRowSum(0) should be(1)
    grid.getRowSum(1) should be(0)
    grid.getRowSum(2) should be(0)
    grid.getRowSum(3) should be(1)
    
    grid.getCell(0,0) should be(null)
    grid.getCell(1,1) should be(null)
    grid.getCell(3,3) should be(null)
  } 
  
  
  
}
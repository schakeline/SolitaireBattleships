package de.htwg.scala.solitairebattleship.model

import org.scalatest.FlatSpec
import org.scalatest.Matchers
import de.htwg.scala.solitairebattleship.model.exception.ShipCollisionException

class GridSpec extends FlatSpec with Matchers {
	
  "Creating a grid with size < 2" should "throw an IllegalArgumentException" in {
    an [IllegalArgumentException] should be thrownBy new Grid(1)
  }
  
  "A grid" should "have a size" in {
    val g = new Grid(2)
    g.size should be(2)
  }
  
  "PlaceShip" should "validate its parameters" in {
    var s:Ship = null
    val gridSize = 4
    val g = new Grid(gridSize)
    
    an [IllegalArgumentException] should be thrownBy g.placeShip(s, 0, 0, Orientation.Horizontal)
    
    s = new Ship(0, 2)
    an [IndexOutOfBoundsException] should be thrownBy g.placeShip(s, -1, 0, Orientation.Horizontal)
    an [IndexOutOfBoundsException] should be thrownBy g.placeShip(s, gridSize, 0, Orientation.Horizontal)
    an [IndexOutOfBoundsException] should be thrownBy g.placeShip(s, 0, -1, Orientation.Horizontal)
    an [IndexOutOfBoundsException] should be thrownBy g.placeShip(s, 0, gridSize, Orientation.Horizontal)
  }
  
  "PlaceShip" should "validate the ships position" in {
    var s = new Ship(0,2)
    val gridSize = 4
    var g = new Grid(4)
    
    an [ShipCollisionException] should be thrownBy g.placeShip(s, 3, 0, Orientation.Horizontal)
    an [ShipCollisionException] should be thrownBy g.placeShip(s, 0, 3, Orientation.Vertical)
  }
  
  "PlaceShip" should "check if cells are already occupied" in {
    var s1 = new Ship(0,2)
    var s2 = new Ship(0,2)
    val gridSize = 4
    var g = new Grid(4)
    g.placeShip(s1, 0, 0, Orientation.Horizontal)
    an [ShipCollisionException] should be thrownBy g.placeShip(s2, 1, 0, Orientation.Vertical)
    
  }
  
  /*
  "PlaceShip" should "move the ship if it's already placed" in {
    var s = new Ship(0,2)
    val gridSize = 4
    var g = new Grid(4)
    
    g.placeShip(s, 0, 0, Orientation.Horizontal)
    g.placeShip(s, 1, 0, Orientation.Vertical)
  }*/
  
}
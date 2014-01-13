package de.htwg.scala.solitairebattleship.model

import org.scalatest.FlatSpec
import org.scalatest.Matchers
import de.htwg.scala.solitairebattleship.model.exception.ShipCollisionException
import de.htwg.scala.solitairebattleship.util.Orientation
import de.htwg.scala.solitairebattleship.util.Orientation

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
    g = g.placeShip(s1, 0, 0, Orientation.Horizontal)
    an [ShipCollisionException] should be thrownBy g.placeShip(s2, 1, 0, Orientation.Vertical)
    
  }
  
  "PlaceShip" should "move the ship if it's already placed" in {
    var s = new Ship(0,2)
    val gridSize = 4
    var g = new Grid(4)
    
    g = g.placeShip(s, 0, 0, Orientation.Horizontal)
    g = g.placeShip(s, 1, 0, Orientation.Vertical)
    
    g.getCell(0, 0) should be(None)
    g.getCell(1, 0) should be(Some(s))
    g.getCell(1, 1) should be(Some(s))
  }
  
  "A ship" should "have a position" in {
    var s = new Ship(0,4)
    var g = new Grid(8)
    
    g = g.placeShip(s, 1, 0, Orientation.Horizontal)
    g.getCell(1, 0).get should be(s)
    
    g = g.placeShip(s, 1, 0, Orientation.Horizontal)
    g.getCell(1,0).get should be(s)
  }
  
  "A ship" should "be removable" in {
    var s = new Ship(0,4)
    var g = new Grid(8)
    
    g = g.placeShip(s, 1, 0, Orientation.Horizontal)
    g.getCell(1,0).get should be(s)
    
    g = g.removeShip(s)
    g.getCell(1,0) should be(None)
  }
  
  "The RowSum of an Empty Grid" should "be 0" in {
    val g = new Grid(5)
    g.getRowSum(0) should be(0)
  }
  
  "The RowSum" should "be 1" in {
    var g = new Grid(5)
    var s = new Ship(0,2)
    g = g.placeShip(s, 0,0,Orientation.Vertical) 
    g.getRowSum(0) should be (1)
  } 
  
  "The RowSum" should "be 3" in {
    var g = new Grid(5)
    var s = new Ship(0,3)
    g = g.placeShip(s,0,0,Orientation.Horizontal)
    g.getRowSum(0) should be(3)
  }
  
  "The ColumnSum of an Empty Grid" should "be 0" in {
    val g = new Grid(5)
    g.getColumnSum(0) should be(0)
  }
  
  "The ColumnSum" should "be 1" in {
    var g = new Grid(5)
    var s = new Ship(0,3)
    g = g.placeShip(s,0,0,Orientation.Horizontal)
    g.getColumnSum(0) should be(1)
  }
  
  "The ColumnSum" should "be 2" in {
    var g =  new Grid(5)
    var s = new Ship(0,2)
    g = g.placeShip(s,0,0,Orientation.Vertical)
    g.getColumnSum(0) should be(2)
  }
  
  "copy a grid" should "create a deep copy" in {
    var g = new Grid(5)
    val s1 = new Ship(0,1)
    val s2 = new Ship(1,1)
    
    g = g.placeShip(s1, 0,0,Orientation.Horizontal)
    var g2 = g.copy
    g2 = g2.placeShip(s2, 2, 2, Orientation.Horizontal)
    
    g.getCell(0,0) should be(Some(s1))
    g.getCell(2,2) should be(None)
    g2.getCell(0,0) should be(Some(s1))
    g2.getCell(2, 2) should be(Some(s2))
  }
  
  "In a new grid no ship" should "be placed" in {
    val g = new Grid(3)
    
    g.getCell(0,0) should be(None)
  }
}
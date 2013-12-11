package de.htwg.scala.solitairebattleship.controller

import org.scalatest.FlatSpec
import org.scalatest.Matchers
import de.htwg.scala.solitairebattleship.model._
import de.htwg.scala.solitairebattleship.util.Orientation


	class ValidatorSpec extends FlatSpec with Matchers{
		
	  "An empty Grid "should "be valid" in {
		  val g = new Grid(2)
		  Validator.validateNeighborhood(g) should be(true)
	  }
	  
	  
	  "Validate a Grid with diagonal Collision" should "be false" in {
	    val g = new Grid(2)
	    val s1 = new Ship(1,1)
	    val s2 = new Ship(2,1)
	    g.placeShip(s1, 0, 0, Orientation.Horizontal)
	    g.placeShip(s2, 1, 1, Orientation.Horizontal)
	    Validator.validateNeighborhood(g) should be(false)
	  }
	  
	  "Validate a Grid with horizontal Collision " should "be false" in {
	    val g = new Grid(2)
	    val s1 = new Ship(1,1)
	    val s2 = new Ship(2,1)
	    g.placeShip(s1, 0, 0, Orientation.Horizontal)
	    g.placeShip(s2, 1, 0, Orientation.Horizontal)
	    Validator.validateNeighborhood(g) should be(false)
	  }
	  
	  "Validate a Grid with vertical Collision " should "be false" in {
	    val g = new Grid(2)
	    val s1 = new Ship(1,1)
	    val s2 = new Ship(2,1)
	    g.placeShip(s1, 0, 0, Orientation.Horizontal)
	    g.placeShip(s2, 0, 1, Orientation.Horizontal)
	    Validator.validateNeighborhood(g) should be(false)
	  }
	  
	  "Validate a Grid without Collision" should "be true" in {
	    val g = new Grid(3)
	    val s1 = new Ship(1,1)
	    val s2 = new Ship(2,1)
	    g.placeShip(s1, 0, 0, Orientation.Horizontal)
	    g.placeShip(s2, 2, 2, Orientation.Horizontal)
	    Validator.validateNeighborhood(g) should be(true)
	  }
	  
	  "Validate a Grid with diagonal (south-west to north-east) Collision" should "be false" in {
	    val g = new Grid(2)
	    val s1 = new Ship(1,1)
	    val s2 = new Ship(2,1)
	    g.placeShip(s2, 0, 0, Orientation.Horizontal)
	    g.placeShip(s1, 1, 1, Orientation.Horizontal)
	    Validator.validateNeighborhood(g) should be(false)
	  }
	}

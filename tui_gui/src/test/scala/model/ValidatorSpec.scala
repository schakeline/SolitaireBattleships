package de.htwg.scala.solitairebattleship.model

import org.scalatest.FlatSpec
import org.scalatest.Matchers
import de.htwg.scala.solitairebattleship.model._
import de.htwg.scala.solitairebattleship.util.Orientation;


  class ValidatorSpec extends FlatSpec with Matchers{

    "An empty Grid "should "be valid" in {
      val g:IGrid = new Grid(4)
      val res = Validator.validateNeighborhood(g)
      res.isEmpty should be(true)
    }
    
    "Validate the neighborhood with one ship" should "be true" in {
      val g = new Grid(4)
      val s = new Ship(1,2)
      
      g.placeShip(s, 0,0,Orientation.Horizontal)
      
      val res = Validator.validateNeighborhood(g)
      res.isEmpty should be(true)
    }
    
    
    "Validate a Grid with diagonal Collision" should "be false" in {
      var g = new Grid(3)
      val s1 = new Ship(1,1)
      val s2 = new Ship(2,1)
      g = g.placeShip(s1, 0, 0, Orientation.Horizontal)
      g = g.placeShip(s2, 1, 1, Orientation.Horizontal)
      val res = Validator.validateNeighborhood(g)
      res.size should be(2)
      res(0) should be((0,0))
      res(1) should be((1,1))
    }
    
    "Validate a Grid with horizontal Collision " should "be false" in {
      var g = new Grid(2)
      val s1 = new Ship(1,1)
      val s2 = new Ship(2,1)
      g = g.placeShip(s1, 0, 0, Orientation.Horizontal)
      g = g.placeShip(s2, 1, 0, Orientation.Horizontal)
      val res = Validator.validateNeighborhood(g)
      res.length should be(2)
    }
    
    "Validate a Grid with vertical Collision " should "be false" in {
      var g = new Grid(2)
      val s1 = new Ship(1,1)
      val s2 = new Ship(2,1)
      g = g.placeShip(s1, 0, 0, Orientation.Horizontal)
      g = g.placeShip(s2, 0, 1, Orientation.Horizontal)
      val res = Validator.validateNeighborhood(g)
      res.length should be(2)
    }
    
    "Validate a Grid without Collision" should "be true" in {
      var g = new Grid(3)
      val s1 = new Ship(1,1)
      val s2 = new Ship(2,1)
      g = g.placeShip(s1, 0, 0, Orientation.Horizontal)
      g = g.placeShip(s2, 2, 2, Orientation.Horizontal)
      val res = Validator.validateNeighborhood(g)
      res.length should be(0)
    }
    
    "Validate a Grid with diagonal (south-west to north-east) Collision" should "be false" in {
      var g = new Grid(2)
      val s1 = new Ship(1,1)
      val s2 = new Ship(2,1)
      g = g.placeShip(s2, 0, 0, Orientation.Horizontal)
      g = g.placeShip(s1, 1, 1, Orientation.Horizontal)
      val res = Validator.validateNeighborhood(g)
      res.length should be(2)
    }
  }

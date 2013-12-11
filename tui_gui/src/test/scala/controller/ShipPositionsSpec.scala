package de.htwg.scala.solitairebattleship.controller

import org.scalatest.FlatSpec
import org.scalatest.Matchers
import de.htwg.scala.solitairebattleship.model._
import de.htwg.scala.solitairebattleship.util.Orientation




	class ShipPositionsSpec extends FlatSpec with Matchers{
	  
		"There" should "not be a valid position in a grid(0)" in {
		  val s = new Ship(1,1)
		  val pos = new ShipPositions(s,0)
		  pos.possiblePositions.size should be (0)
		}
		
		"In a Grid of size 1" should "be two valid positions" in {
		  val s = new Ship(1,1)
		  val pos = new ShipPositions(s,1)
		  pos.possiblePositions.size should be(2)
		}
		
		"placing a ship with size 3 in a grid of size 2" should "not be possible" in {
		  val s = new Ship(1,3)
		  val pos = new ShipPositions(s,2)
		  pos.possiblePositions.size should be(0)
		}
		
		"Place a ship with size 3 in a grid of size 3" should "be possible" in {
		  val s = new Ship(1,3)
		  val pos = new ShipPositions(s,3)
		  pos.possiblePositions.size should be(6)
		}

		"Remove a Position" should "reduce the number of valid positions" in {
		  val s = new Ship(1,1)
		  val pos = new ShipPositions(s,1)
		  pos.rmPosition(0,0,Orientation.Horizontal)
		  pos.possiblePositions.size should be(1)
		}
		
	}

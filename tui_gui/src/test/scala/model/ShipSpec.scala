package de.htwg.scala.solitairebattleship.model

import org.scalatest.FlatSpec
import org.scalatest.Matchers


class ShipSpec extends FlatSpec with Matchers {
	
	"A ship" should "have an id" in {
	  var s = new Ship(0,2)
	  s.id should be(0)
	}
	
	"Creating a ship with an id < 0" should "throw an IllegalArgumentException" in {
	  an [IllegalArgumentException] should be thrownBy new Ship(-1,2)
	}
	
	"A ship" should "have a size" in {
	  var s = new Ship(1, 1)
	  s.size should be(1)
	}
	
	"Creating a ship with a size < 1" should "throw an IllegalArgumentException" in {
	  an [IllegalArgumentException] should be thrownBy new Ship(0,-1)
	}
	
	"To String" should "return the correct string" in {
	  val s1 = new Ship(1,2)
	  s1.toString should be("<11>")
	  val s2 = new Ship(2,1)
	  s2.toString should be("<2>")
	}
}
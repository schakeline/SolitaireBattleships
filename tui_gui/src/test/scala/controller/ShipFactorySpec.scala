package de.htwg.scala.solitairebattleship.controller

import org.scalatest.FlatSpec
import org.scalatest.Matchers
import de.htwg.scala.solitairebattleship.model

class ShipFactorySpec extends FlatSpec with Matchers {
  
  "The Game Factory" should "return the correct nof Ships" in {
    ShipFactory.getShips(10).size should be(10)
    ShipFactory.getShips(9).size should be(8)
    ShipFactory.getShips(8).size should be(7)
    ShipFactory.getShips(7).size should be(7)
    ShipFactory.getShips(6).size should be(6)
    ShipFactory.getShips(5).size should be(4)
    ShipFactory.getShips(4).size should be(3)
    val ships = ShipFactory.getShips(3)
    ships.size should be(3)
    ships(0).id should be (0)
    ships(0).size should be(1)
    
    an [Exception] should be thrownBy ShipFactory.getShips(2)
    an [Exception] should be thrownBy ShipFactory.getShips(11)
  } 
  
  

}
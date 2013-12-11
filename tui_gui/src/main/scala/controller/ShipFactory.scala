package de.htwg.scala.solitairebattleship.controller

import de.htwg.scala.solitairebattleship.model._

object ShipFactory {

  def getShips(gridSize:Int) = {
    gridSize match {
      case 10 => ships10by10
      case 9  => (new Ship(7,1)) :: ships8by8  
      case 8  => ships8by8
      case 7  => (new Ship(6,1)) :: ships6by6 
      case 6  => ships6by6
      case 5  => (new Ship(0,3)) :: (new Ship(1,2)) :: (new Ship(2,1)) :: (new Ship(3,1)) :: Nil
      case 4  => (new Ship(0,2)) :: (new Ship(1,1)) :: (new Ship(2,1)) :: Nil
      case 3  => (new Ship(0,1)) :: (new Ship(1,1)) :: (new Ship(2,1)) :: Nil
      case _  => throw new Exception
    }
  }
  
  private def ships10by10:List[Ship] = {
    var ships:List[Ship] = Nil
    for (i <- 0 until 10) {
      var s = i match {
        case x if x < 4 => 1
        case x if x < 7 => 2
        case x if x < 9 => 3
        case _ => 4
      }
      ships = (new Ship(i, s)) :: ships
    }
    ships
  }
  
  private def ships8by8:List[Ship] = {
    var ships:List[Ship] = Nil
    for (i <- 0 until 7) {
      var s = i match {
        case x if x < 3 => 1
        case x if x < 5 => 2
        case x if x < 6 => 3
        case _ => 4
      }
      ships = (new Ship(i, s)) :: ships
    }
    ships
  }
  
    private def ships6by6:List[Ship] = {
    var ships:List[Ship] = Nil
    for (i <- 0 until 6) {
      var s = i match {
        case x if x < 3 => 1
        case x if x < 5 => 2
        case _ => 3
      }
      ships = (new Ship(i, s)) :: ships
    }
    ships
  }
  
}
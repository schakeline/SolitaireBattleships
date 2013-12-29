package de.htwg.scala.solitairebattleship.view

import de.htwg.scala.solitairebattleship.model.Ship

object Formatter {
  
  def coordinatesForGrid(size:Int):List[String] = (0 until size).map(i =>(i+65).toChar.toString).toList

  def shipsToShipIdList(ships:List[Ship]):List[String] = {
    var idList:List[String] = Nil
    ships.sortBy(s => s.id).foreach(s => idList = idList :+ s.id.toString)
    idList
  }
}
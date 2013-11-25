package de.htwg.scala.solitairebattleship.controller

import de.htwg.scala.solitairebattleship.model.Orientation._

class Position(val x:Int, val y:Int, val orientation:Orientation){
  if(x<0 || y<0)throw new IllegalArgumentException()
}
package de.htwg.scala.solitairebattleship.model

class Ship(val id: Int, val size: Int) {
  if (id < 0 || size < 1) throw new IllegalArgumentException()

  override def toString = {
    var str = "<"
    for (i <- 0 until size) str += id.toString
    str + ">"
  }
}
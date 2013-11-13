package de.htwg.scala.solitairebattleship.model

class Ship (val id:Int, val size:Int) {
	if (id < 0 || size < 1) throw new IllegalArgumentException()
}
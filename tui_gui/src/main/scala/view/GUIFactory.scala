package de.htwg.scala.solitairebattleship.view

//import scala.concurrent._
import scala.actors.Actor
import de.htwg.scala.solitairebattleship.controller.GameController

class GUIFactory(controller: GameController) extends Actor {

  def act() = {
    val gui = new GUI(controller)
    gui.open
  }
}
package de.htwg.scala.solitairebattleship.view

import scala.concurrent._
import de.htwg.scala.solitairebattleship.controller.GameController

class GUIFactory(controller:GameController) extends Runnable{

  def run() = {
    val gui = new GUI(controller)
    gui.open
  }
}
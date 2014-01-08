package de.htwg.scala.solitairebattleship.view

import scala.actors.Actor
import de.htwg.scala.solitairebattleship.controller.GameController

class TUIFactory(controller:GameController) extends Actor{

  def act() = {
    val tui = new TUI(controller)
    
    // start input from tui
    do {
        print(Console.BLUE + "> " + Console.RESET)
    }
    while (tui.processUserInput(readLine()))

    // if user enters q in TUI exit will be executed
    System.exit(0)
  }
}
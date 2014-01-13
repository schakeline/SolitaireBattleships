package de.htwg.scala.solitairebattleship

import de.htwg.scala.solitairebattleship.controller.GameController
import de.htwg.scala.solitairebattleship.view.TUI
import de.htwg.scala.solitairebattleship.view.GUI
import de.htwg.scala.solitairebattleship.view.GUIFactory

object SolitaireBattleship {
  
  def main (args:Array[String]) = {
    // init controller
    val controller = new GameController

    //start GUI
    val g = new GUIFactory(controller)
    g.start
    
    // start TUI
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